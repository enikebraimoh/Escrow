package ng.adashi.network

import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginResponse
    suspend fun signUp(signUpDetails : SignUpDetails) : SignUpResponse
    suspend fun GetWallet(wallet_id : String) : AgentWalletResponse

}