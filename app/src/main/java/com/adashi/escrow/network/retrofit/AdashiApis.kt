package ng.adashi.network.retrofit


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
import ng.adashi.domain_models.login.LoginToken
import retrofit2.http.*

interface AdashiApis {

    @POST("auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginToken

    @POST("auth/signup")
    suspend fun SignUp(
        @Body signUpDetails: SignUpDetails
    ) : SignUpResponse

    @POST("auth/activation")
    suspend fun verifyEmail(
        @Body emailverify: EmailVerifyDetails
    ) : VerifyEmailResponse

    @GET("transactions/me")
    suspend fun getAllTransactions() : TransactionsResponse

    @GET("orders/all")
    suspend fun getAllOrders() : AllOrdersResponse

    @POST("transactions/new")
    suspend fun CreateTransaction(
        @Body trans : sampleTrans
    ) : NewTransactionBodyResponse

    @GET("user/balance")
    suspend fun getWalletBalancce() : WalletBalance

    @GET("users/me")
    suspend fun getCurrentUserData() : UserData

    @GET("/")
    suspend fun getNigerianBanks() : MutableList<ListOfBanksItem>

    @PATCH("transactions/{transactionId}")
    suspend fun patchTransaction(
        @Path("transactionId") transactionId : String,
        @Body status : PatchShipingStatus
    ) : ShipmentPatchResponse

    @GET("others/validate-bvn/{bvn}")
    suspend fun VerifyBvn(
        @Path("bvn") bvn : String
    ) : BvnResponse

    @GET("resolve-bank/{accountNumber}")
    suspend fun checkAccountName(
        @Path("accountNumber") number : String
    ) : AccountNameResponse

    @POST("user/bvn")
    suspend fun addBvn(
        @Body bvn : BVN
    ) : UserResponse

    @POST("user/add-bank")
    suspend fun addBank(
        @Body bank : AddBankDetails
    ) : AddBankResponse

    @GET("user/banks")
    suspend fun getBanks(
    ) : GetAllBanksResponse

}