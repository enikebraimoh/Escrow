package com.adashi.escrow.models.addbank

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddBankResponse(
    @SerializedName("data")
    @Expose
    val data: Data,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("status")
    @Expose
    val status: String
)