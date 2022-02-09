package com.adashi.escrow.models.user

data class User(
    val __v: Int,
    val _id: String,
    val accounts: List<Any>,
    val activation_code: ActivationCode,
    val bvn: String,
    val createdAt: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone_number: String,
    val updatedAt: String,
    val verified: Boolean
)