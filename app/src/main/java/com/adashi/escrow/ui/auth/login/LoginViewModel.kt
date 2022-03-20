package com.adashi.escrow.ui.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
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

class LoginViewModel(val app: Application, val authRepository: AuthRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null

    val session = SessionManager(app.applicationContext)

    private val _navigateToDashboard = MutableLiveData<Boolean>()
    val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard

    private val _login = MutableLiveData<DataState<LoginToken>>()
    val login: LiveData<DataState<LoginToken>> get() = _login

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: MutableLiveData<String?> get() = _passwordError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    fun login() {
        if (verifyEmail()) {
            if (verifyPassword()) {
                logUsersIn(LoginDetails(email!!, password!!))
            }
        }
    }

    //call the login function from the repository
    fun logUsersIn(login: LoginDetails) {
        viewModelScope.launch {
            authRepository.LogUserNewIn(login).onEach { state ->
                _login.value = state
                when(state){
                    is DataState.Success<LoginToken> -> {
                        session.saveAuthToken(state.data.accessToken)
                        App.token = state.data.accessToken
                        navigate()
                    }
                }
            }
        }
    }

    fun navigate(){
        _navigateToDashboard.value = true
    }

    fun navigationDone(){
        _navigateToDashboard.value = false
    }

    private fun verifyEmail(): Boolean {
        return if (email == null || email == "") {
            _emailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = null
            true
        } else {
            _emailError.value = "invalid email"
            false
        }
    }

    private fun verifyPassword(): Boolean {
        return if (password == null || password == "") {
            _passwordError.value = "Password field cannot be blank"
            false
        } else if (password!!.length < 6) {
            _passwordError.value = "Password field must have at least 6 characters"
            false
        } else {
            _passwordError.value = null
            true
        }
    }

}