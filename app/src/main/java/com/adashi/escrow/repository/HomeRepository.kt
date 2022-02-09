package ng.adashi.repository

import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.Transaction
import com.adashi.escrow.models.createtransaction.order.allorders.AllOrdersResponse
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderDetails
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.userdata.UserData
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import com.adashi.escrow.ui.withdraw.models.WithdrawDetails
import com.adashi.escrow.ui.withdraw.models.WithdrawResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException

class HomeRepository(private val networkDataSource: NetworkDataSource) {

    suspend fun getWalletAgentDetails () : Flow<DataState<WalletBalance>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.GetWalletBalance()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    suspend fun createOrder(details: NewOrderDetails) : Flow<DataState<NewOrderResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.createOrder(details)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    suspend fun getAllTransactions() : Flow<DataState<TransactionsResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.getAllTransactions()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    suspend fun getAllOrders() : Flow<DataState<AllOrdersResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.getAllOrders()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    suspend fun getCurrentUserData() : Flow<DataState<UserData>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.getCurrentUserData()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

    suspend fun PatchTransaction (trans_id : String, tras: PatchShipingStatus) : Flow<DataState<ShipmentPatchResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.patchShippingStatus(trans_id,tras)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            when (e){
                is IOException -> emit(DataState.Error(e))
                is HttpException -> {
                    val status = e.code()
                    val res = convertErrorBody(e)
                    emit(DataState.GenericError(status, res))
                }
            }
        }
    }

}