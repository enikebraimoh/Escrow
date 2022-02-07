package com.adashi.escrow.core

import android.graphics.Color
import android.os.Build
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.adashi.escrow.R
import com.adashi.escrow.models.createtransaction.order.allorders.Order
import com.adashi.escrow.models.shipmentpatch.Transaction
import com.google.android.material.textfield.TextInputLayout
import ng.adashi.utils.convertStringToDate
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("verify_field")
fun verifyField(field: TextInputLayout, error: String?) {
    if (error != null) {
        field.isErrorEnabled = true
        field.error = error
    } else {
        field.isErrorEnabled = false
    }
}

@BindingAdapter("amount")
fun Amount(tv: TextView, data: Any) {
    tv.text = data.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("format_date_time")
fun formatDateTime(view: TextView, date: String) {
    val formatedDate = convertStringToDate(date)
    val formatedTime = formatedDate.format(DateTimeFormatter.ofPattern("hh:mm a"))

    view.text = "${formatedDate.dayOfWeek}, $formatedTime"

}

@BindingAdapter("currency_converter")
fun currencyConverter(tv: TextView, data: Any) {
    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
    newformat.setMaximumFractionDigits(0)
    newformat.setCurrency(Currency.getInstance("NGN"))
    val bal = data
    tv.text = newformat.format(bal)

}

@BindingAdapter("convert_tag")
fun convertTag(tv: TextView, data: Order) {
    if (data.settlement_status ==0) {
       tv.setBackgroundColor(Color.GREEN)
    } else if (data.settlement_status == null) {
        tv.setBackgroundColor(Color.YELLOW)
    }else if(data.settlement_status == 1){
        tv.setBackgroundColor(Color.RED)
    }
}

@BindingAdapter("selectProductType")
fun selectProductType(productTypeField: AutoCompleteTextView, productTypes: ArrayAdapter<String>) {
    productTypeField.setAdapter(productTypes)
}