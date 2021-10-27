package com.adashi.escrow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.adashi.escrow.databinding.SucessBottomSheetDialogueBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import ng.adashi.utils.RoundedBottomSheet

class ShowSucessDialogFragment(val data : NewTransactionBodyResponse, val click : (id:Int) -> Unit) : RoundedBottomSheet() {
    lateinit var binding : SucessBottomSheetDialogueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.sucess_bottom_sheet_dialogue,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.price.text = data.data.transaction.price.toString()
        binding.status.text = data.data.transaction.status.toString()
        binding.title.text = data.data.transaction.title
        binding.url.text = data.data.url

        binding.url.setOnClickListener {
            click(0)
        }

    }

}