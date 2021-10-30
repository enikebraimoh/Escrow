package com.adashi.escrow.ui.transactions

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.createtransaction.Transaction
import com.adashi.escrow.models.shipmentpatch.ShipmentPatchResponse
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.wallet.TransactionsResponse
import com.adashi.escrow.models.wallet.WalletBalance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.network.SessionManager
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class TransactionsViewModel(val app: Application, val homeRepository: HomeRepository) : ViewModel() {

    private val _allTransactions = MutableLiveData<DataState<TransactionsResponse>>()
    val allTransactions: LiveData<DataState<TransactionsResponse>> get() = _allTransactions

    private val _patch = MutableLiveData<DataState<ShipmentPatchResponse>>()
    val patch: LiveData<DataState<ShipmentPatchResponse>> get() = _patch


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    //call the function from the repository
    fun  getAllTransactions(){
        viewModelScope.launch {
            homeRepository.getAllTransactions().onEach { state ->
                _allTransactions.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun patchTransaction(trans_id : String, tras: PatchShipingStatus) {
        viewModelScope.launch {
            homeRepository.PatchTransaction(trans_id,tras).onEach { state ->
                _patch.value = state
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