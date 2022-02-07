package com.adashi.escrow.models.createtransaction.order.allorders

data class Order(
    val __v: Int,
    val _id: String,
    val buyer: Buyer,
    val createdAt: String,
    val description: String,
    val fee_paid_by: Int,
    val order_id: String,
    val pay_link: String,
    val payment_status: Int,
    val price: Int,
    val settlement_status : Int,
    val product_category: String,
    val quantity: Int,
    val shipment_status: Int,
    val title: String,
    val total: Int,
    val updatedAt: String,
    val user: String
)