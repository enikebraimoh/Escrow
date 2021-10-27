package com.adashi.escrow.ui.dashboard

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentDashboardBinding
import com.adashi.escrow.models.createtransaction.*
import com.adashi.escrow.models.wallet.Balances
import com.adashi.escrow.models.wallet.WalletBalance
import com.adashi.escrow.ui.createtransaction.CreateTransactionFactory
import com.adashi.escrow.ui.createtransaction.CreateTransactionFragmentDirections
import com.adashi.escrow.ui.createtransaction.CreateTransactionViewModel
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import java.text.NumberFormat
import java.util.*

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    override fun start() {

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = DashboardFactory(application, HomeRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            DashboardViewModel::class.java
        )


        viewModel.wallet_ballance.observe(this, { response ->
            when (response) {
                is DataState.Success<WalletBalance> -> {
                    showSnackBar("Refreshed")
                    binding.refreshLayout.isRefreshing = false
                    binding.balance = Balances(
                        response.data.data.total_balance,
                        response.data.data.pending_transaction,
                        response.data.data.dispute_order
                    )
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
                        findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
                    } else {
                        showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        Log.d("Testt", "Dashboard fragment")
        Toast.makeText(requireContext(), App.token, Toast.LENGTH_SHORT).show()

        binding.createTransaction.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToCreateTransactionFragment())
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getWalletBalancce()
        }

        // fake data
        val buyer = BuyerX("", "", 455678)
        val seller = SellerX("", "", 455678)
        val trans = VirtualAccount("", "", "")
        var listData = mutableListOf<Transaction>(
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "laptop", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Mouse", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Charger", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "laptop", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Mouse", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Charger", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "laptop", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Mouse", 7, "", "",
                "", "", trans
            ),
            Transaction(
                44, "", buyer, "2021-10-27T13:02:36.689Z", "", "", "",
                78.67, "", 4, seller, "Charger", 7, "", "",
                "", "", trans
            ),
        )

        initAdapter(listData)

    }

    private fun initAdapter(data: MutableList<Transaction>) {
        val adapter = TransactionsAdapter {
            showSnackBar(it.title)
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(data)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun formatText(number: Int) {

    }

}