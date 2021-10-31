package com.adashi.escrow.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentSettingsBinding
import ng.adashi.core.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun start() {
        super.start()
        binding.withdrawalbanks.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWithdrawalBanksFragment())
        }

    }

}