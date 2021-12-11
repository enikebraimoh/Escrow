package com.adashi.escrow.ui.settings.bankaccounts.mono

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MonoAccountCode(
    @SerializedName("code")
    @Expose
    val code: String
)