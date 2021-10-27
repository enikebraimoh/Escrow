package com.adashi.escrow.core

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.adashi.escrow.models.createtransaction.Transaction
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
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

@BindingAdapter("currency_converter")
fun currencyConverter(tv: TextView, data: Int) {
    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
    newformat.setMaximumFractionDigits(0)
    newformat.setCurrency(Currency.getInstance("NGN"))
    val bal = data.toInt()
    tv.text = newformat.format(bal)

}

@BindingAdapter("selectProductType")
fun selectProductType(productTypeField: AutoCompleteTextView, productTypes: ArrayAdapter<String>) {
    productTypeField.setAdapter(productTypes)
}