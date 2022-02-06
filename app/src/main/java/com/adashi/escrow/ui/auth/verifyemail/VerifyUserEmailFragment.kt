package com.adashi.escrow.ui.auth.verifyemail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentVerifyUserEmailBinding
import com.adashi.escrow.ui.auth.login.LoginFragmentDirections
import com.adashi.escrow.ui.auth.verifyemail.models.EmailVerifyDetails
import com.adashi.escrow.ui.auth.verifyemail.models.VerifyEmailResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.core.BaseFragment
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.SessionManager
import ng.adashi.utils.DataState

@AndroidEntryPoint
class VerifyUserEmailFragment : BaseFragment<FragmentVerifyUserEmailBinding>(R.layout.fragment_verify_user_email) {

    val viewModel: VerifyEmailViewModel by viewModels()

    val args : VerifyUserEmailFragmentArgs by navArgs()

    override fun start() {
        super.start()
        val id_deep_link = args.id
        val activation_code = args.activationCode


        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )


        val data = EmailVerifyDetails(activation_code,id_deep_link)
        viewModel.verifyUser(data)


        viewModel.navigateToDashboard.observe(this, {
            if (it) {
                val editor = prefs.edit()
                editor.putBoolean(SessionManager.LOGINSTATE, true)
                editor.apply()
                findNavController().navigate(VerifyUserEmailFragmentDirections.actionVerifyUserEmailFragmentToDashboardFragment())
                viewModel.navigationDone()
            }
        })

        viewModel.login.observe(this, { response ->
            when (response) {
                is DataState.Success<VerifyEmailResponse> -> {
                  //  displayProgressBar(false)
                }
                is DataState.Error -> {
                   // displayProgressBar(false)
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    //displayProgressBar(false)
                    if (response.code == 401) {
                        showSnackBar("Invalid email or password")
                    } else {
                        showSnackBar(response.error?.message.toString())
                        // Toast.makeText(requireContext(), response.code.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                DataState.Loading -> {
                   // displayProgressBar(true)
                }
            }
        })

       // Toast.makeText(requireContext(), "$id_deep_link,  $activation_code", Toast.LENGTH_SHORT).show()
        

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}