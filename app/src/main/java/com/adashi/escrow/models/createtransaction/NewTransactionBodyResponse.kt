package com.adashi.escrow.models.createtransaction

import com.adashi.escrow.models.shipmentpatch.Data

data class NewTransactionBodyResponse(
    val data: Data,
    val message: String,
    val status: String
)