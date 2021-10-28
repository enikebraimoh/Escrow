package com.adashi.escrow.models.shipmentpatch

data class ShipmentPatchResponse(
    val data: Data,
    val message: String,
    val status: String
)