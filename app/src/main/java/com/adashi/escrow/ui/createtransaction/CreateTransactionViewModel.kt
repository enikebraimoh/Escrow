package com.adashi.escrow.ui.createtransaction

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.R
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState

class CreateTransactionViewModel(val app: Application, val authRepository: AuthRepository) :
    ViewModel() {

    // for seller
    var seller_name: String? = null
    var seller_email: String? = null
    var seller_phone: String? = null

    // for buyer
    var buyer_name: String? = null
    var buyer_email: String? = null
    var buyer_phone: String? = null

    // for product
    var transaction_title: String? = null
    var product_type: String? = null
    var product_name: String? = null
    var product_description: String? = null
    var product_quantity: String? = null
    var product_price: String? = null
    var whopays: String? = null
    var payment_method: String? = null


    val session = SessionManager(app.applicationContext)

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin


    private val _signup = MutableLiveData<DataState<SignUpResponse>>()
    val signup: LiveData<DataState<SignUpResponse>> get() = _signup

    // live data for error response
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError
    private val _firstNameError = MutableLiveData<String>()
    val firstNameError: LiveData<String> get() = _firstNameError
    private val _lastNameError = MutableLiveData<String>()
    val lastNameError: LiveData<String> get() = _lastNameError
    private val _name = MutableLiveData<String>()
    val myname: LiveData<String> get() = _name
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError


    val productType = app.resources.getStringArray(R.array.product_type)
    val WhoPays = app.resources.getStringArray(R.array.who_is_paying)
    val PaymentMethod = app.resources.getStringArray(R.array.payment_method)

    val productTypeArrayAdapter =
        ArrayAdapter(app.applicationContext, R.layout.dropdown_item, productType)
    val WhoPaysArrayAdapter = ArrayAdapter(app.applicationContext, R.layout.dropdown_item, WhoPays)
    val PaymentMethodArrayAdapter =
        ArrayAdapter(app.applicationContext, R.layout.dropdown_item, PaymentMethod)


    fun register() {

        //logUsersIn(SignUpDetails(firstName!!,lastName!!, email!!,password!!))
    }

    //call the login function from the repository
    fun logUsersIn(details: SignUpDetails) {
        viewModelScope.launch {
            authRepository.SignUpUser(details).onEach { state ->
                _signup.value = state
            }.launchIn(viewModelScope)
        }
    }


    fun navigateButtonClicked() {
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone() {
        _navigateToLogin.value = false
    }


}