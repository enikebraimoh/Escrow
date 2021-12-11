package com.adashi.escrow.ui.settings.bankaccounts.mono

import com.adashi.escrow.models.addbank.Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MonoAccountResponse(
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