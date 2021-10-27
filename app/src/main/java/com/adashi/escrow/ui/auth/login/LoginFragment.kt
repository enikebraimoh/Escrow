package com.adashi.escrow.ui.auth.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun start() {
        Log.d("Testt","Login fragment")

        Toast.makeText(requireContext(), App.token, Toast.LENGTH_SHORT).show()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = LoginFactory(application, AuthRepository(network))

        binding.register.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterAuthFragment())
        }

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(LoginViewModel::class.java)
        binding.data = viewModel

        if (!App.token.isNullOrEmpty()){
            viewModel.navigate()
        }

        binding.lifecycleOwner = this

        viewModel.navigateToDashboard.observe(this,{
            if (it){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
                viewModel.navigationDone()
            }
        })

            viewModel.login.observe(this, { response ->
                when (response) {
                    is DataState.Success<LoginToken> -> {
                        displayProgressBar(false)
                        showSnackBar("success")
                        viewModel.navigate()
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
                binding.loginButton.visibility = View.INVISIBLE
                binding.emailField.isEnabled = false
                binding.passwordField.isEnabled = false
            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.emailField.isEnabled = true
                binding.passwordField.isEnabled = true
            }
        }

    }

}