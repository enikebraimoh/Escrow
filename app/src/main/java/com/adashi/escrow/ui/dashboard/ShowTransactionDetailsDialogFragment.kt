package com.adashi.escrow.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.adashi.escrow.R
import com.adashi.escrow.databinding.TransactionDetailsBottomSheetDialogueBinding
import com.adashi.escrow.models.createtransaction.order.allorders.Order
import ng.adashi.utils.RoundedBottomSheet
import java.text.NumberFormat
import java.util.*

class ShowTransactionDetailsDialogFragment(
    val data: Order,
    val resend: (url: String) -> Unit,
    val click: (id: Int, patch: Int) -> Unit
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


        val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
        newformat.setMaximumFractionDigits(0)
        newformat.setCurrency(Currency.getInstance("NGN"))
        val bal = data.total

        Toast.makeText(requireContext(),data.settlement_status.toString(), Toast.LENGTH_SHORT).show()

        binding.transPrice.text = newformat.format(bal)

        binding.transaStatus.text = if (data.payment_status == 0) "Paid" else "Not Paid"

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

        binding.transaStatus.text = if (data.payment_status == 0) "Paid" else "Not Paid"


        if (data.payment_status == 0) {
            when (data.settlement_status) {
                0 -> {
                    binding.ShipmentStatusField.isEnabled = false
                    binding.resend.visibility = View.GONE
                    binding.urlButton.visibility = View.GONE
                    binding.ShipmentStatusField.hint = when {
                        data.shipment_status == 2 -> "Delivered"
                        data.shipment_status == 0 -> "Shipped"
                        else -> "Not Shipped"
                    }
                    binding.transaStatus.text = "Settled"
                    binding.shipmentStatus.isEnabled = false
                    binding.urlButton.isEnabled = false
                    binding.transTag.setBackgroundColor(Color.GREEN)
                }
                1 -> {
                    binding.transaStatus.text = "Dispute"
                    binding.ShipmentStatusField.isEnabled = false
                    binding.transaStatus.text = "Order in Dispute"
                    binding.resend.visibility = View.GONE
                    binding.urlButton.setBackgroundColor(Color.parseColor("#E6E6E6"))
                    binding.shipmentStatus.isEnabled = false
                    binding.urlButton.isEnabled = false
                    binding.transTag.setBackgroundColor(Color.RED)
                }
                else -> {
                    binding.ShipmentStatusField.isEnabled = true
                    binding.shipmentStatus.isEnabled = true
                    binding.urlButton.isEnabled = true
                    binding.urlButton.setBackgroundColor(Color.parseColor("#0F2965"))
                    binding.shipmentStatus.hint = when (data.shipment_status) {
                        2 -> "Delivered"
                        1 -> "Not Shipped"
                        else -> "Shipped"
                    }
                    binding.transaStatus.text = "Buyer Payment Success"
                    binding.transaStatus.text =
                        if (data.payment_status == 0) "Buyer has Paid" else "Buyer has Not Paid"
                    binding.transTag.setBackgroundColor(Color.parseColor("#F7A74F"))
                }
            }
        }
        else {
            binding.transaStatus.text = "Pending"
            binding.ShipmentStatusField.isEnabled = false
            binding.transaStatus.text = "Awating buyer's payment"
            binding.shipmentStatus.isEnabled = false
            binding.urlButton.isEnabled = false
            binding.urlButton.setBackgroundColor(Color.parseColor("#E6E6E6"))
            binding.transTag.setBackgroundColor(Color.parseColor("#F7A74F"))
        }



        binding.urlButton.setOnClickListener {
            val value = binding.shipmentStatus.text.toString()
            if (validateShiping()) {
                click(0, if (value == "Order has Delivered") 2 else 0)
                dismiss()
            }
        }

        binding.resend.setOnClickListener {
            // val value = binding.shipmentStatus.text.toString()
            resend(data.pay_link)
            dismiss()
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