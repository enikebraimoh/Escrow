package com.adashi.escrow.ui.transactions

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentTransactionsBinding
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.createtransaction.order.allorders.Order
import com.adashi.escrow.models.shipmentpatch.Transaction
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.ui.dashboard.*
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.App
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

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        viewModel.allOrders.observe(this, { response ->
            when (response) {
                is DataState.Success<AllOrdersResponse> -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    initAdapter(response.data.data.orders)
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
                        App.token = null
                        showSnackBar("token expired, please login again")
                        val editor = prefs.edit()
                        editor.putBoolean(SessionManager.LOGINSTATE, false)
                        editor.apply()
                        val session = SessionManager(requireContext().applicationContext)
                        session.clearAuthToken()
                        findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToLoginFragment())
                    } else {
                        // showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })

        viewModel.getAllOrders()

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

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun updateTransaction(transaction_id: String, body: PatchShipingStatus) {
        viewModel.patchTransaction(transaction_id, body)

    }

}