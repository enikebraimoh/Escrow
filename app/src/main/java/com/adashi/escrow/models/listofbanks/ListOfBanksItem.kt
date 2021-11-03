package com.adashi.escrow.models.listofbanks

data class ListOfBanksItem(
    val code: String,
    val logo: String,
    val name: String,
    val slug: String,
    val ussd: String
)