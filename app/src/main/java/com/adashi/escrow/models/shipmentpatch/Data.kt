package com.adashi.escrow.models.shipmentpatch

import com.adashi.escrow.models.createtransaction.order.allorders.Order

data class Data(
    val transaction: Order
)