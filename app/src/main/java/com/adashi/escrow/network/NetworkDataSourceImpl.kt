package com.adashi.escrow.network

import com.adashi.escrow.models.accountname.BankDetails
import com.adashi.escrow.models.accountname.NewAccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderDetails
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderResponse
import com.adashi.escrow.models.listofbanks.AllNigerianBanksResponse
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.NewBVNFeedBack
import com.adashi.escrow.models.userdata.UserData
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import com.adashi.escrow.ui.auth.verifyemail.models.EmailVerifyDetails
import com.adashi.escrow.ui.auth.verifyemail.models.VerifyEmailResponse
import com.adashi.escrow.ui.withdraw.models.WithdrawDetails
import com.adashi.escrow.ui.withdraw.models.WithdrawResponse
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSource

class NetworkDataSourceImpl : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails): LoginToken {
        return RetrofitInstance.api.Login(loginDetails)
    }

    override suspend fun signUp(details: SignUpDetails): SignUpResponse {
        return RetrofitInstance.api.SignUp(details)
    }

    override suspend fun verifyEmail(details: EmailVerifyDetails): VerifyEmailResponse {
        return RetrofitInstance.api.verifyEmail(details)
    }

    override suspend fun withdraw(bank: WithdrawDetails): WithdrawResponse {
        return RetrofitInstance.api.withdraw(bank)
    }

    override suspend fun GetWalletBalance(): WalletBalance {
        return RetrofitInstance.api.getWalletBalancce()
    }

    override suspend fun getAllTransactions(): TransactionsResponse {
        return RetrofitInstance.api.getAllTransactions()
    }

    override suspend fun getAllOrders(): AllOrdersResponse {
        return RetrofitInstance.api.getAllOrders()
    }

    override suspend fun getCurrentUserData(): UserData {
        return RetrofitInstance.api.getCurrentUserData()
    }

    override suspend fun patchShippingStatus(
        trans_id: String,
        tras: PatchShipingStatus
    ): ShipmentPatchResponse {
        return RetrofitInstance.api.patchTransaction(trans_id, tras)
    }

    override suspend fun getNigerianBanks(): AllNigerianBanksResponse {
        return RetrofitInstance.api.getNigerianBanks()
    }

    override suspend fun VerifyBvn(bvn: String): BvnResponse {
        return RetrofitInstance.api.VerifyBvn(bvn)
    }

    override suspend fun addBvn(bvn: BVN): NewBVNFeedBack {
        return RetrofitInstance.api.addBvn(bvn)
    }

    override suspend fun checkAccountName(number: BankDetails): NewAccountNameResponse {
        return RetrofitInstance.api.checkAccountName(number)
    }

    override suspend fun addBank(bank: AddBankDetails): AddBankResponse {
        return RetrofitInstance.api.addBank(bank)
    }

    override suspend fun getBanks(): GetAllBanksResponse {
        return RetrofitInstance.api.getBanks()
    }

    override suspend fun createOrder(details: NewOrderDetails): NewOrderResponse {
        return RetrofitInstance.api.createOrder(details)
    }

}