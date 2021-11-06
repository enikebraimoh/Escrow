package ng.adashi.network

import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.Transaction
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkDataSource {
    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun signUp(signUpDetails : SignUpDetails) : SignUpResponse
    suspend fun CreateTransaction(details : sampleTrans) : NewTransactionBodyResponse
    suspend fun GetWalletBalance() : WalletBalance
    suspend fun getAllTransactions() : TransactionsResponse
    suspend fun getCurrentUser() : UserResponse
    suspend fun patchShippingStatus(trans_id : String, tras: PatchShipingStatus) : ShipmentPatchResponse
    suspend fun getNigerianBanks() : MutableList<ListOfBanksItem>
    suspend fun VerifyBvn(bvn: String) : BvnResponse
    suspend fun addBvn(bvn: BVN) : UserResponse
    suspend fun checkAccountName(number: String) : AccountNameResponse
    suspend fun addBank(bank: AddBankDetails) : AddBankResponse
    suspend fun getBanks() : GetAllBanksResponse

}