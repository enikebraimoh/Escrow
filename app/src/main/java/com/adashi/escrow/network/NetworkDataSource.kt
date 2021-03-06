package ng.adashi.network

import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.accountname.BankDetails
import com.adashi.escrow.models.accountname.NewAccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderDetails
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderResponse
import com.adashi.escrow.models.listofbanks.AllNigerianBanksResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.NewBVNFeedBack
import com.adashi.escrow.models.user.UserResponse
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
import ng.adashi.domain_models.login.LoginToken
import retrofit2.http.Body

interface NetworkDataSource {
    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun signUp(signUpDetails : SignUpDetails) : SignUpResponse
    suspend fun createOrder(details : NewOrderDetails) : NewOrderResponse
    suspend fun GetWalletBalance() : WalletBalance
    suspend fun getAllTransactions() : TransactionsResponse
    suspend fun getAllOrders() : AllOrdersResponse
    suspend fun getCurrentUserData() : UserData
    suspend fun patchShippingStatus(trans_id : String, tras: PatchShipingStatus) : ShipmentPatchResponse
    suspend fun getNigerianBanks() : AllNigerianBanksResponse
    suspend fun VerifyBvn(bvn: String) : BvnResponse
    suspend fun addBvn(bvn: BVN) : NewBVNFeedBack
    suspend fun checkAccountName(number: BankDetails) : NewAccountNameResponse
    suspend fun addBank(bank: AddBankDetails) : AddBankResponse
    suspend fun getBanks() : GetAllBanksResponse
    suspend fun verifyEmail(details : EmailVerifyDetails): VerifyEmailResponse
    suspend fun withdraw(bank : WithdrawDetails) : WithdrawResponse
}