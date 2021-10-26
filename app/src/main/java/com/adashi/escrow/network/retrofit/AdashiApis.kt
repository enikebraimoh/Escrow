package ng.adashi.network.retrofit


import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdashiApis {

    @POST("api/v1/auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginResponse

    @POST("api/v1/auth/signup")
    suspend fun SignUp(
        @Body signUpDetails: SignUpDetails
    ) : SignUpResponse

    @GET("api/v1/agent/wallet/{wallet_Id}")
    suspend fun GetAgentWallet(
        @Path("wallet_Id") wallet_Id : String
    ) : AgentWalletResponse

}