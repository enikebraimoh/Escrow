package com.adashi.escrow.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun start() {
        super.start()
        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val session = SessionManager(requireContext().applicationContext)

        binding.withdrawalbanks.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWithdrawalBanksFragment())
        }
        binding.logout.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean(SessionManager.LOGINSTATE, true)
            editor.apply()
            App.token = null
            session.clearAuthToken()
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
        }

        binding.editLayout.setOnClickListener {
            showSnackBar("work in progress")
        }
        binding.beneficLayout .setOnClickListener {
            showSnackBar("work in progress")
        }
        binding.changePassLayout.setOnClickListener {
            showSnackBar("work in progress")
        }
        binding.changePinLayout.setOnClickListener {
            showSnackBar("work in progress")
        }
        binding.helpLayout.setOnClickListener {
            showSnackBar("work in progress")
        }
        binding.aboutLayout.setOnClickListener {
            showSnackBar("work in progress")
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}