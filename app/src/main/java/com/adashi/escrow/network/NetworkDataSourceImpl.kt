package ng.adashi.network

import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse

class NetworkDataSourceImpl : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails): LoginResponse {
           val response = RetrofitInstance.api.Login(loginDetails)
           return response
    }

    override suspend fun signUp(details : SignUpDetails): SignUpResponse {
        val response = RetrofitInstance.api.SignUp(details)
        return response
    }

    override suspend fun GetWallet(wallet_id: String): AgentWalletResponse {
        val response = RetrofitInstance.api.GetAgentWallet(wallet_id)
        return response
    }

}