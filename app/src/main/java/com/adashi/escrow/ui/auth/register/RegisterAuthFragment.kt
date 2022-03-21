package com.adashi.escrow.ui.auth.register

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentRegisterAuthBinding
import com.adashi.escrow.models.signup.SignUpResponse
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import com.adashi.escrow.network.NetworkDataSourceImpl
import com.adashi.escrow.repository.AuthRepository
import ng.adashi.utils.DataState


class RegisterAuthFragment : BaseFragment<FragmentRegisterAuthBinding>(R.layout.fragment_register_auth) {

    override fun start() {
        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = RegisterFactory(application, AuthRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            RegisterViewModel::class.java)

        binding.data = viewModel

        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener {
            findNavController().navigate(RegisterAuthFragmentDirections.actionRegisterAuthFragmentToLoginFragment())
        }

        viewModel.navigateToLogin.observe(this,{
            if (it){

                findNavController().navigate(RegisterAuthFragmentDirections.actionRegisterAuthFragmentToVerifyEmailFragment())
                viewModel.navigateToLoginDone()
            }

        })

        viewModel.signup.observe(this, { response ->
            when (response) {
                is DataState.Success<SignUpResponse> -> {
                    displayProgressBar(false)
                    //showSnackBar("Account Created")
                    viewModel.navigateButtonClicked()
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                    showSnackBar(response.error?.message.toString())
                }
                DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.spinKit.visibility = View.VISIBLE
                binding.register.visibility = View.INVISIBLE
                binding.emailField.isEnabled = false
                binding.passwordField.isEnabled = false
            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.register.visibility = View.VISIBLE
                binding.emailField.isEnabled = true
                binding.passwordField.isEnabled = true
            }
        }

    }

}