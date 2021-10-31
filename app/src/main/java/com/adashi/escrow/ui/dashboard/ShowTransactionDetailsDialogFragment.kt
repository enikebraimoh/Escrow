package com.adashi.escrow.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.adashi.escrow.R
import com.adashi.escrow.databinding.SucessBottomSheetDialogueBinding
import com.adashi.escrow.databinding.TransactionDetailsBottomSheetDialogueBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.shipmentpatch.Transaction
import ng.adashi.utils.RoundedBottomSheet

class ShowTransactionDetailsDialogFragment(
    val data: Transaction,
    val click: (id: Int, patch: String) -> Unit
) :
    RoundedBottomSheet() {
    lateinit var binding: TransactionDetailsBottomSheetDialogueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.transaction_details_bottom_sheet_dialogue,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.transPrice.text = data.price.toString()
        binding.transaStatus.text = data.shipment_status
        binding.transTitle.text = data.title
        //binding.shipmentStatus.hint = data.status
        //binding.url.text = data.data.url

        val ShipmentMethod =
            requireActivity().applicationContext.resources.getStringArray(R.array.shipment_method)
        val ShipmentMethodArrayAdapter =
            ArrayAdapter(
                requireActivity().applicationContext,
                R.layout.dropdown_item,
                ShipmentMethod
            )

        binding.shipmentStatus.setAdapter(ShipmentMethodArrayAdapter)

        binding.transaStatus.text = data.shipment_status


        if (data.is_paid) {
            if (data.settled) {
                binding.ShipmentStatusField.isEnabled = false
                binding.ShipmentStatusField.hint = data.shipment_status
                binding.transaStatus.text = "Settled"
                binding.shipmentStatus.isEnabled = false
                binding.urlButton.isEnabled = false
                binding.transTag.setBackgroundColor(Color.GREEN)
            } else {
                if (data.dispute) {
                    binding.transaStatus.text = "Dispute"
                    binding.ShipmentStatusField.isEnabled = false
                    binding.transaStatus.text = "Order in Dispute"
                    binding.shipmentStatus.isEnabled = false
                    binding.urlButton.isEnabled = false
                    binding.transTag.setBackgroundColor(Color.RED)
                } else {
                    binding.ShipmentStatusField.isEnabled = true
                    binding.shipmentStatus.isEnabled = true
                    binding.urlButton.isEnabled = true
                    binding.transaStatus.text = "Buyer Payment Success"
                    binding.transaStatus.text = data.shipment_status
                    binding.transTag.setBackgroundColor(Color.GREEN)
                }
            }

        } else {
            binding.transaStatus.text = "Pending"
            binding.ShipmentStatusField.isEnabled = false
            binding.transaStatus.text = "Awating buyer's payment"
            binding.shipmentStatus.isEnabled = false
            binding.urlButton.isEnabled = false
            binding.transTag.setBackgroundColor(Color.YELLOW)
        }

        binding.urlButton.setOnClickListener {
            val value = binding.shipmentStatus.text.toString()
            if (validateShiping()) {
                click(0, binding.shipmentStatus.text.toString())
                dismiss()
            }
        }
    }

    private fun validateShiping(): Boolean {
        return if (binding.shipmentStatus.text.toString() == "" || binding.shipmentStatus.text.toString() == null) {
            binding.ShipmentStatusField.isErrorEnabled = true
            binding.ShipmentStatusField.error = "this field cannot be left blank"
            false
        } else {
            binding.ShipmentStatusField.isErrorEnabled = false
            true
        }
    }

}