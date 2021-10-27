package com.adashi.escrow.models.createtransaction

data class VirtualAccount(
    val bank_code: String,
    val bank_name: String,
    val vnuban: String
)