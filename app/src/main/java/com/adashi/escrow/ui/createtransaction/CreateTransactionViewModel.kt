package com.adashi.escrow.ui.createtransaction

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.R
import com.adashi.escrow.models.createtransaction.Buyer
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.createtransaction.NewTransactionRequestBody
import com.adashi.escrow.models.createtransaction.Seller
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.signup.SignUpDetails
import com.adashi.escrow.models.signup.SignUpResponse
import com.adashi.escrow.models.user.UserResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class CreateTransactionViewModel(val app: Application, val homeRepository: HomeRepository) :
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


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin


    private val _transaction = MutableLiveData<DataState<NewTransactionBodyResponse>>()
    val transaction: LiveData<DataState<NewTransactionBodyResponse>> get() = _transaction

    private val _user = MutableLiveData<DataState<UserResponse>>()
    val user: LiveData<DataState<UserResponse>> get() = _user


    // live data for error response

    /*For Sellers*/
    private val _sellerNameError = MutableLiveData<String>()
    val sellerNameError: LiveData<String> get() = _sellerNameError
    private val _sellerEmailError = MutableLiveData<String>()
    val sellerEmailError: LiveData<String> get() = _sellerEmailError
    private val _sellerPhoneError = MutableLiveData<String>()
    val sellerPhoneError: LiveData<String> get() = _sellerPhoneError

    /*For Buyers*/
    private val _buyerNameError = MutableLiveData<String>()
    val buyerNameError: LiveData<String> get() = _buyerNameError
    private val _buyerEmailError = MutableLiveData<String>()
    val buyerEmailError: LiveData<String> get() = _buyerEmailError
    private val _buyerPhoneError = MutableLiveData<String>()
    val buyerPhoneError: LiveData<String> get() = _buyerPhoneError

    /*For Product*/
    private val _transactionTitleNameError = MutableLiveData<String>()
    val transactionTitleNameError: LiveData<String> get() = _transactionTitleNameError
    private val _productTypeError = MutableLiveData<String>()
    val productTypeError: LiveData<String> get() = _productTypeError
    private val _productTypeNameError = MutableLiveData<String>()
    val productTypeNameError: LiveData<String> get() = _productTypeNameError
    private val _productDescriptionError = MutableLiveData<String>()
    val productDescriptionError: LiveData<String> get() = _productDescriptionError
    private val _productQuantityError = MutableLiveData<String>()
    val productQuantityError: LiveData<String> get() = _productQuantityError
    private val _productPriceError = MutableLiveData<String>()
    val productPriceError: LiveData<String> get() = _productPriceError
    private val _whoPaysError = MutableLiveData<String>()
    val whoPaysError: LiveData<String> get() = _whoPaysError
    private val _paymentMethodError = MutableLiveData<String>()
    val paymentMethodError: LiveData<String> get() = _paymentMethodError


    val productType = app.resources.getStringArray(R.array.product_type)
    val WhoPays = app.resources.getStringArray(R.array.who_is_paying)
    val PaymentMethod = app.resources.getStringArray(R.array.payment_method)

    val productTypeArrayAdapter =
        ArrayAdapter(app.applicationContext, R.layout.dropdown_item, productType)
    val WhoPaysArrayAdapter = ArrayAdapter(app.applicationContext, R.layout.dropdown_item, WhoPays)
    val PaymentMethodArrayAdapter =
        ArrayAdapter(app.applicationContext, R.layout.dropdown_item, PaymentMethod)


    fun CreateNewTransaction() {
        if (validateTransactionTitle()) {
            if (validateSellerName()) {
                if (validateSellerEmail()) {
                    if (validateSellerPhone()) {
                        if (validateBuyerName()) {
                            if (validateBuyerEmail()) {
                                if (validateBuyerPhone()) {
                                    if (validateProductType()) {
                                        if (validateProductName()) {
                                            if (validateProductDescription()) {
                                                if (validateProductQuantity()) {
                                                    if (validateProductPrice()) {
                                                        if (validateWhoPays()) {
                                                            if (validatePaymentMethod()) {
                                                                val buyer =
                                                                    com.adashi.escrow.models.Buyer(
                                                                        buyer_email!!,
                                                                        buyer_name!!,
                                                                        buyer_phone?.toLong()!!
                                                                    )
                                                                val seller =
                                                                    com.adashi.escrow.models.Seller(
                                                                        seller_email!!,
                                                                        seller_name!!,
                                                                        seller_phone?.toLong()!!
                                                                    )
                                                                val transaction = sampleTrans(
                                                                    buyer,
                                                                    product_description!!,
                                                                    whopays!!,
                                                                    payment_method!!,
                                                                    product_price!!.toDouble(),
                                                                    product_type!!,
                                                                    product_quantity?.toInt()!!,
                                                                    seller,
                                                                    transaction_title!!,
                                                                    (product_price!!.toInt() * product_quantity!!.toInt())
                                                                )
                                                                CreateTransaction(transaction)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //call the login function from the repository
    fun CreateTransaction(details: sampleTrans) {
        viewModelScope.launch {
            homeRepository.CreateTransaction(details).onEach { state ->
                _transaction.value = state
            }.launchIn(viewModelScope)
        }
    }



    fun navigateButtonClicked() {
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone() {
        _navigateToLogin.value = false
    }


    private fun validateSellerEmail(): Boolean {
        return if (seller_email == null || seller_email == "") {
            _sellerEmailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(seller_email).matches()) {
            _sellerEmailError.value = null
            true
        } else {
            _sellerEmailError.value = "invalid email"
            false
        }
    }

    private fun validateSellerName(): Boolean {
        return if (seller_name == null || seller_name!! == "") {
            _sellerNameError.value = "this field cannot be left blank"
            false
        } else if (seller_name!!.contains("[0-9]".toRegex())) {
            _sellerNameError.value = "name cannot contain numbers"
            false
        } else if ((seller_name!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _sellerNameError.value = "name cannot contain special characters"
            false
        } else {
            _sellerNameError.value = null
            true
        }
    }

    private fun validateSellerPhone(): Boolean {
        return if (seller_phone == "" || seller_phone == null) {
            _sellerPhoneError.value = "this field cannot be left blank"
            false
        } else if (seller_phone!!.length < 11) {
            _sellerPhoneError.value = "invalid phone number"
            false
        } else {
            _sellerPhoneError.value = null
            true
        }

    }

    private fun validateBuyerEmail(): Boolean {
        return if (buyer_email == null || buyer_email == "") {
            _buyerEmailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(buyer_email).matches()) {
            _buyerEmailError.value = null
            true
        } else {
            _buyerEmailError.value = "invalid email"
            false
        }
    }

    private fun validateBuyerName(): Boolean {
        return if (buyer_name == null || buyer_name!! == "") {
            _buyerNameError.value = "this field cannot be left blank"
            false
        } else if (buyer_name!!.contains("[0-9]".toRegex())) {
            _buyerNameError.value = "name cannot contain numbers"
            false
        } else if ((buyer_name!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _buyerNameError.value = "name cannot contain special characters"
            false
        } else {
            _buyerNameError.value = null
            true
        }
    }

    private fun validateBuyerPhone(): Boolean {
        return if (buyer_phone == "" || buyer_phone == null) {
            _buyerPhoneError.value = "this field cannot be left blank"
            false
        } else if (buyer_phone!!.length < 11) {
            _buyerPhoneError.value = "invalid phone number"
            false
        } else {
            _buyerPhoneError.value = null
            true
        }

    }

    private fun validateTransactionTitle(): Boolean {
        return if (transaction_title == null || transaction_title!! == "") {
            _transactionTitleNameError.value = "this field cannot be left blank"
            false
        }  else if (transaction_title!!.length > 20) {
            _productDescriptionError.value = "Description should be at least 10 characters long"
            false
        }else {
            _transactionTitleNameError.value = null
            true
        }
    }

    private fun validateProductType(): Boolean {
        return if (product_type == "" || product_type == null) {
            _productTypeError.value = "this field cannot be left blank"
            false
        } else {
            _productTypeError.value = null
            true
        }
    }

    private fun validateProductName(): Boolean {
        return if (product_name == null || product_name!! == "") {
            _productTypeNameError.value = "this field cannot be left blank"
            false
        }else {
            _productTypeNameError.value = null
            true
        }
    }

    private fun validateProductDescription(): Boolean {
        return if (product_description == null || product_description!! == "") {
            _productDescriptionError.value = "this field cannot be left blank"
            false
        } else if (product_description!!.length < 40) {
            _productDescriptionError.value = "Description should be at least 40 characters long"
            false
        } else {
            _productDescriptionError.value = null
            true
        }
    }

    private fun validateProductQuantity(): Boolean {
        return if (product_quantity == null || product_quantity!! == "") {
            _productQuantityError.value = "this field cannot be left blank"
            false
        } else if (product_quantity!!.length > 1000) {
            _productQuantityError.value = "quantity is too large"
            false
        } else {
            _productQuantityError.value = null
            true
        }
    }

    private fun validateProductPrice(): Boolean {
        return if (product_price == null || product_price!! == "") {
            _productPriceError.value = "this field cannot be left blank"
            false
        } else if (product_price!!.length > 500) {
            _productPriceError.value = "price is too low"
            false
        } else {
            _productPriceError.value = null
            true
        }
    }

    private fun validateWhoPays(): Boolean {
        return if (whopays == "" || whopays == null) {
            _whoPaysError.value = "this field cannot be left blank"
            false
        } else {
            _whoPaysError.value = null
            true
        }
    }

    private fun validatePaymentMethod(): Boolean {
        return if (payment_method == "" || payment_method == null) {
            _paymentMethodError.value = "this field cannot be left blank"
            false
        } else {
            _paymentMethodError.value = null
            true
        }
    }


}