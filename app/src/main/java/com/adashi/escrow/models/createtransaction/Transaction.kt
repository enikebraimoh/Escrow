package com.adashi.escrow.models.createtransaction

data class Transaction(
    val __v: Int,
    val _id: String,
    val buyer: BuyerX,
    val createdAt: String,
    val description: String,
    val fee_paid_by: String,
    val image: String,
    val payment_method: String,
    val price: Double,
    val product_category: String,
    val quantity: Int,
    val seller: SellerX,
    val title: String,
    val total: Int,
    val transactionId: String,
    val updatedAt: String,
    val user: String,
    val virtual_account: VirtualAccount
)