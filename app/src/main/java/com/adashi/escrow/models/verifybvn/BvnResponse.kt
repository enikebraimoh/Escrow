package com.adashi.escrow.models.verifybvn

data class BvnResponse(
    val data : Data,
    val message: String,
    val status: String
)