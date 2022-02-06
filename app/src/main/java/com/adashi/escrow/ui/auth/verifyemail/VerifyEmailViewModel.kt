package com.adashi.escrow.ui.auth.verifyemail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.ui.auth.verifyemail.models.EmailVerifyDetails
import com.adashi.escrow.ui.auth.verifyemail.models.VerifyEmailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel
@Inject
    constructor
        (val app: Application, val authRepository: AuthRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null

    val session = SessionManager(app.applicationContext)

    private val _navigateToDashboard = MutableLiveData<Boolean>()
    val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard

    private val _login = MutableLiveData<DataState<VerifyEmailResponse>>()
    val login: LiveData<DataState<VerifyEmailResponse>> get() = _login


    fun verifyUser(login: EmailVerifyDetails) {
        viewModelScope.launch {
            authRepository.VerifyEmail(login).onEach { state ->
                _login.value = state
                when(state){
                    is DataState.Success<VerifyEmailResponse> -> {
                        session.saveAuthToken(state.data.accessToken)
                        App.token = state.data.accessToken
                        navigate()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun navigate(){
        _navigateToDashboard.value = true
    }

    fun navigationDone(){
        _navigateToDashboard.value = false
    }

}