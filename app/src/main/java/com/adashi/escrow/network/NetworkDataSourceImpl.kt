package ng.adashi.network

import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.userdata.UserData
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import com.adashi.escrow.ui.auth.verifyemail.models.EmailVerifyDetails
import com.adashi.escrow.ui.auth.verifyemail.models.VerifyEmailResponse
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

    override suspend fun verifyEmail(details : EmailVerifyDetails): VerifyEmailResponse {
        val response = RetrofitInstance.api.verifyEmail(details)
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

    override suspend fun getAllOrders(): AllOrdersResponse {
        val response = RetrofitInstance.api.getAllOrders()
        return response
    }

    override suspend fun getCurrentUserData(): UserData {
        val response = RetrofitInstance.api.getCurrentUserData()
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

    override suspend fun VerifyBvn(bvn: String): BvnResponse {
        val response = RetrofitInstance.api.VerifyBvn(bvn)
        return response
    }

    override suspend fun addBvn(bvn: BVN): UserResponse {
        val response = RetrofitInstance.api.addBvn(bvn)
        return response
    }

    override suspend fun checkAccountName(number: String): AccountNameResponse {
        val response = RetrofitInstance.api.checkAccountName(number)
        return response
    }

    override suspend fun addBank(bank: AddBankDetails): AddBankResponse {
        val response = RetrofitInstance.api.addBank(bank)
        return response
    }

    override suspend fun getBanks(): GetAllBanksResponse {
        val response = RetrofitInstance.api.getBanks()
        return response
    }

    override suspend fun CreateTransaction(details: sampleTrans) : NewTransactionBodyResponse {
        val response = RetrofitInstance.api.CreateTransaction(details)
        return response
    }

}