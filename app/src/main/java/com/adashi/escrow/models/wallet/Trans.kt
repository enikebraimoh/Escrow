package com.adashi.escrow.models.wallet

import com.adashi.escrow.models.shipmentpatch.Transaction

data class Trans(
    val transactions: MutableList<Transaction>,
)