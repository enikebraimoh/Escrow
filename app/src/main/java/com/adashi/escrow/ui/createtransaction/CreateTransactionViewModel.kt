package com.adashi.escrow.ui.createtransaction

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.R
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderDetails
import com.adashi.escrow.models.createtransaction.order.neworder.NewOrderResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class CreateTransactionViewModel(val app: Application, val homeRepository: HomeRepository) :
    ViewModel() {

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


    private val _order = MutableLiveData<DataState<NewOrderResponse>>()
    val order: LiveData<DataState<NewOrderResponse>> get() = _order

 /*   private val _user = MutableLiveData<DataState<UserResponse>>()
    val user: LiveData<DataState<UserResponse>> get() = _user

*/
    // live data for error response

    /*For Buyers*/
    private val _buyerNameError = MutableLiveData<String?>()
    val buyerNameError: LiveData<String?> get() = _buyerNameError
    private val _buyerEmailError = MutableLiveData<String?>()
    val buyerEmailError: LiveData<String?> get() = _buyerEmailError
    private val _buyerPhoneError = MutableLiveData<String?>()
    val buyerPhoneError: LiveData<String?> get() = _buyerPhoneError

    /*For Product*/
    private val _transactionTitleNameError = MutableLiveData<String?>()
    val transactionTitleNameError: LiveData<String?> get() = _transactionTitleNameError
    private val _productTypeError = MutableLiveData<String?>()
    val productTypeError: LiveData<String?> get() = _productTypeError
    private val _productDescriptionError = MutableLiveData<String?>()
    val productDescriptionError: LiveData<String?> get() = _productDescriptionError
    private val _productQuantityError = MutableLiveData<String?>()
    val productQuantityError: LiveData<String?> get() = _productQuantityError
    private val _productPriceError = MutableLiveData<String?>()
    val productPriceError: LiveData<String?> get() = _productPriceError
    private val _whoPaysError = MutableLiveData<String?>()
    val whoPaysError: LiveData<String?> get() = _whoPaysError
    private val _paymentMethodError = MutableLiveData<String?>()
    val paymentMethodError: LiveData<String?> get() = _paymentMethodError


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
                        if (validateBuyerName()) {
                            if (validateBuyerEmail()) {
                                if (validateBuyerPhone()) {
                                    if (validateProductType()) {
                                            if (validateProductDescription()) {
                                                if (validateProductQuantity()) {
                                                    if (validateProductPrice()) {
                                                        if (validateWhoPays()) {
                                                            if (validatePaymentMethod()) {
                                                                val buyer =
                                                                    com.adashi.escrow.models.createtransaction.order.neworder.Buyer(
                                                                        buyer_email!!,
                                                                        buyer_name!!,
                                                                        buyer_phone!!
                                                                    )

                                                                val transaction = NewOrderDetails (
                                                                    buyer,
                                                                    product_description!!,
                                                                    if (whopays!! == "Buyer") 0 else if (whopays!! == "Seller") 1 else 2,
                                                                    payment_method!!,
                                                                    product_price!!.toInt(),
                                                                    product_type!!,
                                                                    product_quantity?.toInt()!!,
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



    //call the login function from the repository
    fun CreateTransaction(details: NewOrderDetails) {
        viewModelScope.launch {
            homeRepository.createOrder(details).onEach { state ->
                _order.value = state
            }.launchIn(viewModelScope)
        }
    }



    fun navigateButtonClicked() {
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone() {
        _navigateToLogin.value = false
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

    private fun validateProductDescription(): Boolean {
        return if (product_description == null || product_description!! == "") {
            _productDescriptionError.value = "this field cannot be left blank"
            false
        } else if (product_description!!.length < 120) {
            _productDescriptionError.value = "Description should be at least 120 characters long"
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