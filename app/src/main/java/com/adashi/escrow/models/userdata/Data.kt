package com.adashi.escrow.models.userdata

data class Data(
    val _id: String,
    val createdAt: String,
    val email: String,
    val exp: Int,
    val first_name: String,
    val iat: Int,
    val last_name: String,
    val phone_number: String,
    val updatedAt: String,
    val wallet: Wallet
)