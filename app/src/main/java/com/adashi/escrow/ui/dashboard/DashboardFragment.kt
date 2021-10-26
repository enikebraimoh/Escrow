package com.adashi.escrow.ui.dashboard

import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentDashboardBinding
import ng.adashi.core.BaseFragment
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    override fun start() {
        val sessionManager = SessionManager(requireContext())
        Log.d("Testt","Dashboard fragment")
        Toast.makeText(requireContext(), App.token, Toast.LENGTH_SHORT).show()

        binding.createTransaction.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToCreateTransactionFragment())
        }

    }

}