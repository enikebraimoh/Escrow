package com.adashi.escrow.models.shipmentpatch

data class Transaction(
    val __v: Int,
    val _id: String,
    val buyer: Buyer,
    val createdAt: String,
    val description: String,
    val fee_paid_by: String,
    val is_paid: Boolean,
    val payment_method: String,
    val price: Int,
    val product_category: String,
    val quantity: Int,
    val seller: Seller,
    val settled: Boolean,
    val shipment_status: String,
    val title: String,
    val total: Int,
    val transactionId: String,
    val updatedAt: String,
    val user: String,
    val dispute: Boolean,
    val virtual_account: VirtualAccount
)