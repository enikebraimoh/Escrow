package com.adashi.escrow.models.signup

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignUpDetails(
    @SerializedName("first_name")
    @Expose
    val firstName: String,
    @SerializedName("last_name")
    @Expose
    val lastName: String,
    @SerializedName("phone_number")
    @Expose
    val phoneNumber: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("password")
    @Expose
    val password: String
)