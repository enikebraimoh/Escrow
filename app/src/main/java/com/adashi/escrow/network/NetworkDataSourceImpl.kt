package ng.adashi.network

import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.Transaction
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.wallet.TransactionsResponse
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

    override suspend fun getAllTransactions(): TransactionsResponse {
        val response = RetrofitInstance.api.getAllTransactions()
        return response
    }

    override suspend fun getCurrentUser(): UserResponse {
        val response = RetrofitInstance.api.getCurrentUser()
        return response
    }

    override suspend fun patchShippingStatus(trans_id : String, tras: PatchShipingStatus): ShipmentPatchResponse {
        val response = RetrofitInstance.api.patchTransaction(trans_id,tras)
        return response
    }

    override suspend fun getNigerianBanks(): MutableList<ListOfBanksItem> {
        val response = RetrofitInstance2.api.getNigerianBanks()
        return response
    }

    override suspend fun CreateTransaction(details: sampleTrans) : NewTransactionBodyResponse {
        val response = RetrofitInstance.api.CreateTransaction(details)
        return response
    }

}