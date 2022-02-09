package com.adashi.escrow.repository

import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.accountname.BankDetails
import com.adashi.escrow.models.accountname.NewAccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.listofbanks.AllNigerianBanksResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.user.NewBVNFeedBack
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.ui.withdraw.models.WithdrawDetails
import com.adashi.escrow.ui.withdraw.models.WithdrawResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SettingsRepository
@Inject
    constructor(private val networkDataSource: NetworkDataSource) {

    suspend fun getBanks() : Flow<DataState<GetAllBanksResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.getBanks()
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

    suspend fun withdraw(details: WithdrawDetails) : Flow<DataState<WithdrawResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.withdraw(details)
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


    suspend fun getNigerianBanks() : Flow<DataState<AllNigerianBanksResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.getNigerianBanks()
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

    suspend fun VerifyBvn (bvn: String) : Flow<DataState<BvnResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.VerifyBvn(bvn)
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

    suspend fun addBvn (bvn: BVN) : Flow<DataState<NewBVNFeedBack>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.addBvn(bvn)
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

    suspend fun checkAccountName (number: BankDetails) : Flow<DataState<NewAccountNameResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.checkAccountName(number)
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

    suspend fun addBank (bank: AddBankDetails) : Flow<DataState<AddBankResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.addBank(bank)
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