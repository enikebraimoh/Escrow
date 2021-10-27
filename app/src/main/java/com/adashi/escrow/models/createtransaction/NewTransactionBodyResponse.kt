package com.adashi.escrow.models.createtransaction

data class NewTransactionBodyResponse(
    val data: Data,
    val message: String,
    val status: String
)