package com.adashi.escrow.models

data class sampleTrans(
    val buyer: Buyer,
    val description: String,
    val fee_paid_by: String,
    val payment_method: String,
    val price: Double,
    val product_category: String,
    val quantity: Int,
    val seller: Seller,
    val title: String,
    val total: Int
)