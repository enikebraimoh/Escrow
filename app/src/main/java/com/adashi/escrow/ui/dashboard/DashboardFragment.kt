package com.adashi.escrow.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentDashboardBinding
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.*
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.createtransaction.order.allorders.Order
import com.adashi.escrow.models.shipmentpatch.Transaction
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.userdata.UserData
import com.adashi.escrow.models.wallet.Balances
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import com.adashi.escrow.ui.dashboard.wallet.BalanceViewPagerAdapter
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.withdraw.WithdrawBottomSheet
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import okhttp3.internal.notifyAll
import java.text.NumberFormat
import java.util.*

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    lateinit var viewModel : DashboardViewModel
    private var firstTime = true

    override fun start() {
        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = DashboardFactory(application, HomeRepository(network))

        val money = mutableListOf("--","--","--")
        val title = mutableListOf("Main Balance", "Pending Balance","Dispute Balance")

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

         viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            DashboardViewModel::class.java
        )

        addFirstDot(binding)

        //Listening to page callbacks
        binding.viewpager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (firstTime)
                        firstTime = false
                    else
                        addDot(position)
                }
            }
        )

        binding.viewpager.adapter = BalanceViewPagerAdapter(money, title)
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        /*viewModel.allTransactions.observe(this, { response ->
            when (response) {
                is DataState.Success<TransactionsResponse> -> {
                    initAdapter(response.data.data.transactions)
                    stopShimmer()
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.GenericError -> {
                    showSnackBar(response.error?.message.toString())
                    binding.refreshLayout.isRefreshing = false
                    if (response.code!! == 403) {

                    } else {
                       // showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })*/

        viewModel.allOrders.observe(this, { response ->
            when (response) {
                is DataState.Success<AllOrdersResponse> -> {
                    initAdapter(response.data.data.orders)
                    stopShimmer()
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.GenericError -> {
                    showSnackBar(response.error?.message.toString())
                    binding.refreshLayout.isRefreshing = false
                    if (response.code!! == 403) {

                    } else {
                        // showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        viewModel.currentUser.observe(this, { response ->
            when (response) {
                is DataState.Success<UserData> -> {

                    binding.username.text = resources.getString(R.string.agent_name,response.data.data.first_name)
                    /*val editor = prefs.edit()
                    editor.putString(SessionManager.USER_BVN,response.data.data.bvn)
                    editor.apply()*/

                    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
                    newformat.maximumFractionDigits = 0
                    newformat.currency = Currency.getInstance("NGN")

                    val mainBal = response.data.data.wallet.main_balance.toString()
                    val pending = response.data.data.wallet.pending_balance.toString()
                    val dispute = response.data.data.wallet.dispute_balance.toString()

                    money.addAll(
                        0,
                        listOf(
                            newformat.format(mainBal.toLong()),
                            newformat.format(pending.toLong()),
                            newformat.format(dispute.toLong())
                        )
                    )


                    (binding.viewpager.adapter as BalanceViewPagerAdapter).notifyDataSetChanged()


                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.GenericError -> {
                    showSnackBar(response.error?.message.toString())
                    binding.refreshLayout.isRefreshing = false
                    if (response.code!! == 403) {
                        App.token = null
                        showSnackBar("token expired, please login again")
                        val editor = prefs.edit()
                        editor.putBoolean(SessionManager.LOGINSTATE, false)
                        editor.apply()
                        val session = SessionManager(requireContext().applicationContext)
                        session.clearAuthToken()
                        findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
                    } else {
                         showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        viewModel.patch.observe(this, { response ->
            when (response) {
                is DataState.Success<ShipmentPatchResponse> -> {
                    showSnackBar("Transaction Updated")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.GenericError -> {
                    showSnackBar(response.error?.message.toString())
                    //Toast.makeText(requireContext(), response.code.toString(), Toast.LENGTH_SHORT).show()
                    binding.refreshLayout.isRefreshing = false
                    if (response.code!! == 403) {
                        App.token = null
                        showSnackBar("token expired, please login again")
                        val editor = prefs.edit()
                        editor.putBoolean(SessionManager.LOGINSTATE, false)
                        editor.apply()
                        val session = SessionManager(requireContext().applicationContext)
                        session.clearAuthToken()
                        findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
                    } else {
                         //showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        Log.d("Testt", "Dashboard fragment")

        binding.createTransaction.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToCreateTransactionFragment())
        }

        binding.withdrawIcon.setOnClickListener {
            val WithdrawBS = WithdrawBottomSheet()

            WithdrawBS.show(requireActivity().supportFragmentManager, "something")
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getAllOrders()
            viewModel.getCurrentUserData()
        }

        viewModel.getAllOrders()
        viewModel.getCurrentUserData()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                /**
                 *
                 *  Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
                 *
                 */
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

    }

    private fun initAdapter(data: List<Order>) {
        val adapter = TransactionsAdapter { d ->
            var fr = ShowTransactionDetailsDialogFragment(d) { index , patchString ->
                when (index) {
                    0 -> {
                        updateTransaction(d.order_id , PatchShipingStatus(patchString))
                    }
                }
            }
            fr.show(requireActivity().supportFragmentManager, "added fragm")
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)
    }

    private fun updateTransaction(transaction_id: String, body : PatchShipingStatus) {
        viewModel.patchTransaction(transaction_id,body)

    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun stopShimmer(){
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
    }

    // creates dot indicator for the first enterance of the onBoardingScreen
    private fun addFirstDot(view: FragmentDashboardBinding) {

        view.pos1.setText(Html.fromHtml("&#8226;"))
        view.pos2.setText(Html.fromHtml("&#8226;"))
        view.pos3.setText(Html.fromHtml("&#8226;"))

        view.pos1.textSize = 40f
        view.pos2.textSize = 40f
        view.pos3.textSize = 40f


        view.pos1.setTextColor(Color.parseColor("#C4C4C4"))
        view.pos2.setTextColor(Color.parseColor("#C4C4C4"))
        view.pos3.setTextColor(Color.parseColor("#C4C4C4"))
        view.pos1.setTextColor(Color.parseColor("#FED525"))

    }

    //creates dot indicator
    private fun addDot(position: Int) {
        val textViews = arrayOfNulls<TextView>(3)
        binding?.let { it.liner.removeAllViews() }
        var i = 0
        while (i < 3) {
            textViews[i] = TextView(requireContext())
            textViews[i]?.setText(Html.fromHtml("&#8226;"))
            textViews[i]?.textSize = 40f
            textViews[i]?.setTextColor(Color.parseColor("#C4C4C4"))

            binding?.let { _view ->
                _view.liner.addView(textViews[i])
            }
            i++
        }

        if (textViews.size > 0)
            textViews[position]?.setTextColor(Color.parseColor("#FED525"))
    }



}