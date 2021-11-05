package ng.adashi.network.retrofit


import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
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
import retrofit2.http.*

interface AdashiApis {

    @POST("api/v1/auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginToken

    @POST("api/v1/auth/signup")
    suspend fun SignUp(
        @Body signUpDetails: SignUpDetails
    ) : SignUpResponse

    @GET("api/v1/transactions/me")
    suspend fun getAllTransactions() : TransactionsResponse

    @POST("api/v1/transactions/new")
    suspend fun CreateTransaction(
        @Body trans : sampleTrans
    ) : NewTransactionBodyResponse

    @GET("api/v1/user/balance")
    suspend fun getWalletBalancce() : WalletBalance

    @GET("api/v1/auth/me")
    suspend fun getCurrentUser() : UserResponse

    @GET("/")
    suspend fun getNigerianBanks() : MutableList<ListOfBanksItem>

    @PATCH("api/v1/transactions/{transactionId}")
    suspend fun patchTransaction(
        @Path("transactionId") transactionId : String,
        @Body status : PatchShipingStatus
    ) : ShipmentPatchResponse

    @GET("api/v1/others/validate-bvn/{bvn}")
    suspend fun VerifyBvn(
        @Path("bvn") bvn : String
    ) : BvnResponse

    @GET("api/v1/others/resolve-bank/{accountNumber}")
    suspend fun checkAccountName(
        @Path("accountNumber") number : String
    ) : AccountNameResponse

    @POST("api/v1/user/bvn")
    suspend fun addBvn(
        @Body bvn : BVN
    ) : UserResponse

    @POST("api/v1/user/add-bank")
    suspend fun addBank(
        @Body bank : AddBankDetails
    ) : AddBankResponse

}