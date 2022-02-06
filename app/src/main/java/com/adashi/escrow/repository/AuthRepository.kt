package ng.adashi.repository

import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.ui.auth.verifyemail.models.EmailVerifyDetails
import com.adashi.escrow.ui.auth.verifyemail.models.VerifyEmailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSource
import ng.adashi.utils.DataState
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AuthRepository
@Inject
    constructor(private val networkDataSource: NetworkDataSource) {

    suspend fun LogUserNewIn (loginDetails: LoginDetails) : Flow<DataState<LoginToken>> = flow {
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

    suspend fun VerifyEmail (signUpDetails: EmailVerifyDetails) : Flow<DataState<VerifyEmailResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = networkDataSource.verifyEmail(signUpDetails)
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