package com.adashi.escrow.ui.withdraw.models

data class WithdrawDetails(
    val account_number: String,
    val amount: Int,
    val bank_code: String,
    val password: String
)