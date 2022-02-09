package com.adashi.escrow.ui.withdraw

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.repository.SettingsRepository
import com.adashi.escrow.ui.withdraw.models.WithdrawDetails
import com.adashi.escrow.ui.withdraw.models.WithdrawResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel
@Inject
    constructor(val app: Application, val SettingsRepository: SettingsRepository) : ViewModel() {

    private val _allBanks = MutableLiveData<DataState<GetAllBanksResponse>>()
    val allBanks: LiveData<DataState<GetAllBanksResponse>> get() = _allBanks

    private val _withdrawResponse = MutableLiveData<DataState<WithdrawResponse>>()
    val withdrawResponse: LiveData<DataState<WithdrawResponse>> get() = _withdrawResponse


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    //call the function from the repository
    fun  getAllBanks(){
        viewModelScope.launch {
            SettingsRepository.getBanks().onEach { state ->
                _allBanks.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun  withdraw(details : WithdrawDetails){
        viewModelScope.launch {
            SettingsRepository.withdraw(details).onEach { state ->
                _withdrawResponse.value = state
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