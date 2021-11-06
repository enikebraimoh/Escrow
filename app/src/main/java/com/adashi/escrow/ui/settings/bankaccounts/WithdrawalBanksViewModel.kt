package com.adashi.escrow.ui.settings.bankaccounts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.repository.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.utils.DataState

class WithdrawalBanksViewModel(val app: Application, val SettingsRepository: SettingsRepository) : ViewModel() {

    private val _allBanks = MutableLiveData<DataState<GetAllBanksResponse>>()
    val allBanks: LiveData<DataState<GetAllBanksResponse>> get() = _allBanks


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

    fun navigateButtonClicked() {
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone() {
        _navigateToLogin.value = false
    }

}