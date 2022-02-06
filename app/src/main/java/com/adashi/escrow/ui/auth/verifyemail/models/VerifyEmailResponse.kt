package com.adashi.escrow.ui.auth.verifyemail.models

data class VerifyEmailResponse(
    val accessToken: String,
    val message: String,
    val refreshToken: String,
    val status: String
)