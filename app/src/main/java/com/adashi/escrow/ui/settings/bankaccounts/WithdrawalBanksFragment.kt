package com.adashi.escrow.ui.settings.bankaccounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.ShowSucessDialogFragment
import com.adashi.escrow.databinding.FragmentWithdrawalBanksBinding
import com.adashi.escrow.ui.addbank.AddBankDialogFragment
import ng.adashi.core.BaseFragment

class WithdrawalBanksFragment : BaseFragment<FragmentWithdrawalBanksBinding>(R.layout.fragment_withdrawal_banks){

    override fun start() {
        super.start()

        binding.addBank.setOnClickListener {
            var fr = AddBankDialogFragment{
                when(it){
                    0 ->{

                    }
                }
            }
            fr.show(requireActivity().supportFragmentManager,"added fragm")

        }

    }

}