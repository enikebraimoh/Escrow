package com.adashi.escrow.ui.addbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.adashi.escrow.R
import com.adashi.escrow.databinding.AddBankBottomSheetDialogueBinding
import com.adashi.escrow.databinding.SucessBottomSheetDialogueBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.listofbanks.ListOfBanks
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import ng.adashi.utils.RoundedBottomSheet
import com.adashi.escrow.MainActivity
import com.squareup.picasso.Picasso


class AddBankDialogFragment(val click: (id: Int) -> Unit) : RoundedBottomSheet() {
    lateinit var binding: AddBankBottomSheetDialogueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_bank_bottom_sheet_dialogue, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banks = mutableListOf<ListOfBanksItem>(
            ListOfBanksItem("", "https://nigerianbanks.xyz/logo/access-bank-diamond.png", "GtBank", "", ""),
            ListOfBanksItem("", "https://nigerianbanks.xyz/logo/alat-by-wema.png", "zenith", "", ""),
            ListOfBanksItem("", "https://nigerianbanks.xyz/logo/default-image.png", "First Bank", "", "")
        )

        val BanksArrayAdapter =
            BanksAdapter(requireActivity().applicationContext, banks)

        binding.spinner.adapter = BanksArrayAdapter

        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val clickedItem: ListOfBanksItem = parent.getItemAtPosition(position) as ListOfBanksItem
                val clickedCountryName: String = clickedItem.name

                Toast.makeText(
                    requireContext(),
                    "$clickedCountryName selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        binding.addbank.setOnClickListener {
            //click(0)
        }

    }

}


