package com.adashi.escrow.models.createtransaction.order.allorders

data class AllOrdersResponse(
    val `data`: Data,
    val message: String,
    val status: String
)