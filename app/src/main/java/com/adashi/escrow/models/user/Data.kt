package com.adashi.escrow.models.user

data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: Long,
    val updatedAt: String,
    val userId: String,
    val walletId: String,
    val bvn: String?
)