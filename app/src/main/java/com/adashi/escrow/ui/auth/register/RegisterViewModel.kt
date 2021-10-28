package com.adashi.escrow.ui.auth.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState

class RegisterViewModel(val app: Application, val authRepository: AuthRepository) : ViewModel()  {

    var email: String? = null
    var phoneNumber: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null

    val session = SessionManager(app.applicationContext)


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin


    private val _signup = MutableLiveData<DataState<SignUpResponse>>()
    val signup: LiveData<DataState<SignUpResponse>> get() = _signup

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    private val _firstNameError = MutableLiveData<String>()
    val firstNameError: LiveData<String> get() = _firstNameError

    private val _lastNameError = MutableLiveData<String>()
    val lastNameError: LiveData<String> get() = _lastNameError

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError

    fun register() {
        if (verifyEmail()) {
            if (validateFirstName()) {
                if (validateLastName()) {
                    if (validateName()) {
                        if (verifyPassword()) {
                            logUsersIn(SignUpDetails(phoneNumber!!,firstName!!,lastName!!, email!!,password!!))
                        }
                    }
                }
            }
        }
    }

    //call the login function from the repository
    fun logUsersIn(details: SignUpDetails) {
        viewModelScope.launch {
            authRepository.SignUpUser(details).onEach { state ->
                _signup.value = state
            }.launchIn(viewModelScope)
        }
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

    fun navigateButtonClicked(){
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone(){
        _navigateToLogin.value = false
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

    private fun validateFirstName(): Boolean {
        return if (firstName == null || firstName!! == "") {
            _firstNameError.value = "this field cannot be left blank"
            false
        } else if (firstName!!.contains("[0-9]".toRegex())) {
            _firstNameError.value = "name cannot contain numbers"
            false
        } else if ((firstName!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _firstNameError.value = "name cannot contain special characters"
            false
        } else if (firstName!!.contains("\\s+".toRegex())) {
            _firstNameError.value = "name cannot contain white space"
            false
        } else {
            _firstNameError.value = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        return if (lastName == null || lastName == "") {
            _lastNameError.value = "this field cannot be left blank"
            false
        } else if (lastName!!.contains("[0-9]".toRegex())) {
            _lastNameError.value = "name cannot contain numbers"
            false
        } else if ((lastName!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _lastNameError.value = "name cannot contain special characters"
            false
        } else if (lastName!!.contains("\\s+".toRegex())) {
            _lastNameError.value = "name cannot contain white space"
            false
        } else {
            _lastNameError.value = null
            true
        }
    }

    private fun validateName(): Boolean {
        return if (phoneNumber == null || phoneNumber == "") {
            _phone.value = "select your Gender"
            false
        } else {
            _phone.value = null
            true
        }
    }

}