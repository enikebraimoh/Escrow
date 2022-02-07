package com.adashi.escrow.models.userdata

data class Wallet(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val dispute_balance: Int,
    val main_balance: Int,
    val pending_balance: Int,
    val updatedAt: String,
    val user: String,
    val wallet_id: String
)