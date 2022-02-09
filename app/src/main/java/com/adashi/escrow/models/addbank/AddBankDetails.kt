package com.adashi.escrow.models.addbank

data class AddBankDetails(
    val account_number: String,
    val account_name: String,
    val bank_name: String,
    val bank_code: String
)