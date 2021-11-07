package com.adashi.escrow.models.verifybvn

data class Data(
    val dateOfBirth: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phoneNumber: String,
    val responseCode: String,
    val responseMessage: String
)