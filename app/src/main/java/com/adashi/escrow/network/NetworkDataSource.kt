package ng.adashi.network

import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.wallet.WalletBalance
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun signUp(signUpDetails : SignUpDetails) : SignUpResponse
    suspend fun CreateTransaction(details : sampleTrans) : NewTransactionBodyResponse
    suspend fun GetWalletBalance() : WalletBalance

}