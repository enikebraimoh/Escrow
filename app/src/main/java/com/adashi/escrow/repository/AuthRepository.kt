package ng.adashi.repository

import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException

class AuthRepository (private val networkDataSource: NetworkDataSource) {

    suspend fun LogUserNewIn (loginDetails: LoginDetails) : Flow<DataState<LoginResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.login(loginDetails)
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

    suspend fun SignUpUser (signUpDetails: SignUpDetails) : Flow<DataState<SignUpResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.signUp(signUpDetails)
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