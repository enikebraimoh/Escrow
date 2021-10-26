package com.adashi.escrow.models.signup

data class Data(
    val Savers: List<Any>? = null,
    val __v: Int? = null,
    val _id: String,
    val agentID: String,
    val email: String,
    val firstName: String,
    val identity: Identity? = null,
    val lastName: String,
    val walletId: String
)