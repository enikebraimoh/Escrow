package com.adashi.escrow.ui.createtransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentCreateTransactionBinding
import com.adashi.escrow.ui.auth.register.RegisterFactory
import com.adashi.escrow.ui.auth.register.RegisterViewModel
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.AuthRepository

class CreateTransactionFragment : BaseFragment<FragmentCreateTransactionBinding>(R.layout.fragment_create_transaction) {
    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = CreateTransactionFactory(application, AuthRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            CreateTransactionViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }


}