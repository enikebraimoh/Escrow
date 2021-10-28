package com.adashi.escrow.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentDashboardBinding
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.*
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.wallet.Balances
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    lateinit var viewModel : DashboardViewModel

    override fun start() {
        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = DashboardFactory(application, HomeRepository(network))

         viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            DashboardViewModel::class.java
        )

        viewModel.getWalletBalancce()
        viewModel.getAllTransactions()
        viewModel.getCurrentUser()

        viewModel.wallet_ballance.observe(this, { response ->
            when (response) {
                is DataState.Success<WalletBalance> -> {
                    // showSnackBar("Refreshed")
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
                        showSnackBar("token expired, please login again")
                        findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
                    } else {
                        showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })
        viewModel.allTransactions.observe(this, { response ->
            when (response) {
                is DataState.Success<TransactionsResponse> -> {
                    initAdapter(response.data.data.transactions)
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
                is DataState.Success<UserResponse> -> {
                    binding.user = response.data.data
                    binding.username.text = resources.getString(R.string.agent_name,response.data.data.firstName)
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

        viewModel.patch.observe(this, { response ->
            when (response) {
                is DataState.Success<ShipmentPatchResponse> -> {
                    showSnackBar("Transaction Update")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    binding.refreshLayout.isRefreshing = false
                }
                is DataState.GenericError -> {
                    showSnackBar(response.error?.message.toString() + response.code.toString())
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

        Log.d("Testt", "Dashboard fragment")
        //Toast.makeText(requireContext(), App.token, Toast.LENGTH_SHORT).show()

        binding.createTransaction.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToCreateTransactionFragment())
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getWalletBalancce()
            viewModel.getAllTransactions()
            viewModel.getCurrentUser()
        }

    }

    private fun initAdapter(data: MutableList<Transaction>) {
        val adapter = TransactionsAdapter { d ->
            var fr = ShowTransactionDetailsDialogFragment(d) { index , patchString ->
                when (index) {
                    0 -> {
                        updateTransaction(d.transactionId, PatchShipingStatus(patchString))
                    }
                }
            }
            fr.show(requireActivity().supportFragmentManager, "added fragm")
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)
    }

    private fun updateTransaction(transaction_id: String, body : PatchShipingStatus) {

        //showSnackBar(transaction_id+"  "+ body)
        viewModel.patchTransaction(transaction_id,body)

    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun formatText(number: Int) {

    }

}