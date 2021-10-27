package com.adashi.escrow.models.wallet

data class Data(
    val dispute_order: Int,
    val pending_transaction: Int,
    val total_balance: Int
)