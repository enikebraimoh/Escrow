package com.adashi.escrow.models.wallet

import com.adashi.escrow.models.createtransaction.Transaction

data class TransactionsResponse(
    val data: Trans,
    val message: String,
    val status: String
)