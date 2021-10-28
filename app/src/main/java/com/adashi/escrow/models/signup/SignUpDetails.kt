package com.adashi.escrow.models.signup

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpDetails(
    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,
    @SerializedName("firstName")
    @Expose
    val firstName: String,
    @SerializedName("lastName")
    @Expose
    val lastName: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("password")
    @Expose
    val password: String
)