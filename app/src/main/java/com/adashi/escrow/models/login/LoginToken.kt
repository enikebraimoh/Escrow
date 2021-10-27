package ng.adashi.domain_models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginToken(
    @SerializedName("accessToken")
    @Expose
    val accessToken: String,

    @SerializedName("refreshToken")
    @Expose
    val refreshToken: String
)