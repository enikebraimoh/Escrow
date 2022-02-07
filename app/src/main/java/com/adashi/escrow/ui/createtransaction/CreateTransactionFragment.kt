package com.adashi.escrow.ui.createtransaction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.ShowSucessDialogFragment
import com.adashi.escrow.databinding.FragmentCreateTransactionBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.ui.auth.register.RegisterFactory
import com.adashi.escrow.ui.auth.register.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.AuthRepository
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

        viewModel.transaction.observe(this, { response ->
            when (response) {
                is DataState.Success<NewTransactionBodyResponse> -> {
                    displayProgressBar(false)
                   // showSnackBar(response.data.data.url)

                    val intent  = ShareCompat.IntentBuilder.from(requireActivity())
                        .setType("text/plain")
                        .setText(getString(R.string.share_transaction,response.data.data.transaction.seller.name,response.data.data.url))
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
                        showSnackBar(response.code.toString())
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

        binding.sellerName.setText("")
        binding.sellerEmail.setText("")
        binding.sellerPhone.setText("")
        binding.buyerName.setText("")
        binding.buyerEmail.setText("")
        binding.buyerPhone.setText("")

        binding.ProductName.setText("")
        binding.ProductType.setText("")
        binding.ProductDescription.setText("")
        binding.ProductQuantity.setText("")
        binding.transactionPrice.setText("")
        binding.whoIsPaying.setText("")
        binding.paymentMethod.setText("")
    }


}