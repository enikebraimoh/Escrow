package com.adashi.escrow.models.createtransaction.order.neworder

data class NewOrderDetails(
    val buyer: Buyer,
    val description: String,
    val fee_paid_by: Int,
    val payment_method: String,
    val price: Int,
    val product_category: String,
    val quantity: Int,
    val title: String,
    val total: Int
)