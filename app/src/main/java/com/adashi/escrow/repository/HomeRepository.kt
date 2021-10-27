package ng.adashi.repository

import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.wallet.WalletBalance
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

    suspend fun CreateTransaction (details: sampleTrans) : Flow<DataState<NewTransactionBodyResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.CreateTransaction(details)
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