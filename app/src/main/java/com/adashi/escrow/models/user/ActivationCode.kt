package com.adashi.escrow.models.user

data class ActivationCode(
    val expires_at: String,
    val token: String
)