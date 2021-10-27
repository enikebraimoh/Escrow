package ng.adashi.network

import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.wallet.WalletBalance
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginToken

class NetworkDataSourceImpl : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails): LoginToken {
           val response = RetrofitInstance.api.Login(loginDetails)
           return response
    }

    override suspend fun signUp(details : SignUpDetails): SignUpResponse {
        val response = RetrofitInstance.api.SignUp(details)
        return response
    }

    override suspend fun GetWalletBalance(): WalletBalance {
        val response = RetrofitInstance.api.getWalletBalancce()
        return response
    }

    override suspend fun CreateTransaction(details: sampleTrans) : NewTransactionBodyResponse {
        val response = RetrofitInstance.api.CreateTransaction(details)
        return response
    }

}