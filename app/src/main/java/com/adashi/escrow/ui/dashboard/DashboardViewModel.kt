package com.adashi.escrow.ui.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.wallet.WalletBalance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class DashboardViewModel(val app: Application, val homeRepository: HomeRepository) : ViewModel() {

    private val _wallet_ballance = MutableLiveData<DataState<WalletBalance>>()
    val wallet_ballance: LiveData<DataState<WalletBalance>> get() = _wallet_ballance


    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin


    //call the function from the repository
    fun getWalletBalancce() {
        viewModelScope.launch {
            homeRepository.getWalletAgentDetails().onEach { state ->
                _wallet_ballance.value = state
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