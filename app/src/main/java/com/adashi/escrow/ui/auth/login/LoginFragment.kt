package com.adashi.escrow.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.domain_models.login.LoginToken
import com.adashi.escrow.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun start() {
        Log.d("Testt", "Login fragment")
        val session = SessionManager(requireContext().applicationContext)

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = LoginFactory(application, AuthRepository(network))
        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(LoginViewModel::class.java)
        binding.data = viewModel

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
        val state = prefs.getBoolean(SessionManager.LOGINSTATE, false)
        if (state) {
            viewModel.navigate()
        }

        binding.register.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterAuthFragment())
        }


        binding.lifecycleOwner = this

        viewModel.navigateToDashboard.observe(this, {
            if (it) {
                val editor = prefs.edit()
                editor.putBoolean(SessionManager.LOGINSTATE, true)
                editor.apply()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
                viewModel.navigationDone()
            }
        })

        viewModel.login.observe(this, { response ->
            when (response) {
                is DataState.Success<LoginToken> -> {
                    displayProgressBar(false)
                    // showSnackBar("Welcome")
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    Toast.makeText(requireContext(), response.error.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("testt",response.error.message.toString())
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                    if (response.code == 401) {
                        showSnackBar("Invalid email or password")
                    } else {
                        showSnackBar(response.error?.message.toString())
                       // Toast.makeText(requireContext(), response.code.toString(), Toast.LENGTH_SHORT).show()
                    }
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