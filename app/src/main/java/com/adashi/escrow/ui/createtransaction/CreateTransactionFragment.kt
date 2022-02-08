package com.adashi.escrow.ui.createtransaction

import android.content.Intent
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.ShowSucessDialogFragment
import com.adashi.escrow.databinding.FragmentCreateTransactionBinding
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderResponse
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import com.adashi.escrow.network.NetworkDataSourceImpl
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState

class CreateTransactionFragment : BaseFragment<FragmentCreateTransactionBinding>(R.layout.fragment_create_transaction) {
    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = CreateTransactionFactory(application, HomeRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            CreateTransactionViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.navigateToLogin.observe(this,{
            if (it) {


            }
        })

        viewModel.order.observe(this, { response ->
            when (response) {
                is DataState.Success<NewOrderResponse> -> {
                    displayProgressBar(false)
                   // showSnackBar(response.data.data.url)

                    val intent  = ShareCompat.IntentBuilder.from(requireActivity())
                        .setType("text/plain")
                        .setText(getString(R.string.share_transaction,response.data.data.url))
                        .intent

                    var fr = ShowSucessDialogFragment(response.data){
                        when(it){
                            0 ->{
                                view?.findNavController()?.popBackStack()
                                startActivity(intent)
                           }
                        }
                    }
                    fr.show(requireActivity().supportFragmentManager,"added fragm")

                    viewModel.navigateToLoginDone()
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                    if (response.code!! == 403){
                        App.token = null

                        showSnackBar("token expired, please login again")
                       findNavController().popBackStack()
                    }
                    else{
                        showSnackBar(response.error?.message.toString())
                    }
                }
                DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

        binding.backButton.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.spinKit.visibility = View.VISIBLE
                binding.loginButton.visibility = View.INVISIBLE

            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
            }
        }

        fun startIntent(int : Intent){
            startActivity((int))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.transactionTitle.setText("")

        binding.buyerName.setText("")
        binding.buyerEmail.setText("")
        binding.buyerPhone.setText("")

        binding.ProductType.setText("")
        binding.ProductDescription.setText("")
        binding.ProductQuantity.setText("")
        binding.transactionPrice.setText("")
        binding.whoIsPaying.setText("")
        binding.paymentMethod.setText("")
    }


}