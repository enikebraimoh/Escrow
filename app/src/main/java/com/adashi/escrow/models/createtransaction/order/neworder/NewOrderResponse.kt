package com.adashi.escrow.models.createtransaction.order.neworder

data class NewOrderResponse(
    val `data`: Data,
    val message: String,
    val status: String
)