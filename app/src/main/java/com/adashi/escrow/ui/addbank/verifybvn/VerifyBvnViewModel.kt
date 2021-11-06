package com.adashi.escrow.ui.addbank.verifybvn

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.models.user.UserResponse
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.repository.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class VerifyBvnViewModel(val app: Application, val settingsRepository: SettingsRepository) : ViewModel() {

    private val _banks = MutableLiveData<DataState<BvnResponse>>()
    val banks: LiveData<DataState<BvnResponse>> get() = _banks

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    fun VerifyBvn(bvn : String) {
        viewModelScope.launch {
            settingsRepository.VerifyBvn(bvn).onEach { state ->
                _banks.value = state
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