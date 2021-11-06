package com.adashi.escrow.ui.addbank.verifybvn.otp

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

class VerifyOtpBvnViewModel(val app: Application, val settingsRepository: SettingsRepository) : ViewModel() {

    private val _banks = MutableLiveData<DataState<UserResponse>>()
    val banks: LiveData<DataState<UserResponse>> get() = _banks

    fun addBvn(bvn : BVN) {
        viewModelScope.launch {
            settingsRepository.addBvn(bvn).onEach { state ->
                _banks.value = state
            }.launchIn(viewModelScope)
        }
    }



}