package com.adashi.escrow.ui.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentTransactionsBinding
import com.adashi.escrow.models.shipmentpatch.Transaction
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.ui.dashboard.DashboardFactory
import com.adashi.escrow.ui.dashboard.DashboardViewModel
import com.adashi.escrow.ui.dashboard.ShowTransactionDetailsDialogFragment
import com.adashi.escrow.ui.dashboard.TransactionsAdapter
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class TransactionsFragment :
    BaseFragment<FragmentTransactionsBinding>(R.layout.fragment_transactions) {

    lateinit var viewModel: TransactionsViewModel

    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = TransactionsFactory(application, HomeRepository(network))

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(TransactionsViewModel::class.java)

        viewModel.allTransactions.observe(this, { response ->
            when (response) {
                is DataState.Success<TransactionsResponse> -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    initAdapter(response.data.data.transactions)
                }
                is DataState.Error -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    showSnackBar(response.error?.message.toString())
                    if (response.code!! == 403) {

                    } else {
                        // showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        viewModel.getAllTransactions()

    }


    private fun initAdapter(data: MutableList<Transaction>) {
        val adapter = TransactionsAdapter { d ->
            var fr = ShowTransactionDetailsDialogFragment(d) { index, patchString ->
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

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun updateTransaction(transaction_id: String, body: PatchShipingStatus) {
        viewModel.patchTransaction(transaction_id, body)

    }

}