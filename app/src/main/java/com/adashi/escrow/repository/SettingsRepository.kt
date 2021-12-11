package com.adashi.escrow.repository

import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.ui.settings.bankaccounts.mono.MonoAccountCode
import com.adashi.escrow.ui.settings.bankaccounts.mono.MonoAccountResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException

class SettingsRepository(private val networkDataSource: NetworkDataSource) {

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


    suspend fun getNigerianBanks() : Flow<DataState<MutableList<ListOfBanksItem>>> = flow {
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

    suspend fun addBvn (bvn: BVN) : Flow<DataState<UserResponse>> = flow {
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

    suspend fun checkAccountName (number: String) : Flow<DataState<AccountNameResponse>> = flow {
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

    suspend fun monoVerifyBank (code: MonoAccountCode) : Flow<DataState<MonoAccountResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.monoVerifyBank(code)
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