package com.adashi.escrow.ui.addbank

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.sampleTrans
import com.adashi.escrow.repository.SettingsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.DataState

class AddBankViewModel(val app: Application, val settingsRepository: SettingsRepository) : ViewModel() {


    private val _banks = MutableLiveData<DataState<MutableList<ListOfBanksItem>>>()
    val banks: LiveData<DataState<MutableList<ListOfBanksItem>>> get() = _banks

    private val _name = MutableLiveData<DataState<AccountNameResponse>>()
    val name: LiveData<DataState<AccountNameResponse>> get() = _name

    private val _res = MutableLiveData<DataState<AddBankResponse>>()
    val res: LiveData<DataState<AddBankResponse>> get() = _res

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    fun getNigerianBanks() {
        viewModelScope.launch {
            settingsRepository.getNigerianBanks().onEach { state ->
                _banks.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun getAccountName(number : String) {
        viewModelScope.launch {
            settingsRepository.checkAccountName(number).onEach { state ->
                _name.value = state
            }.launchIn(viewModelScope)
        }
    }

    fun addBank(bank : AddBankDetails) {
        viewModelScope.launch {
            settingsRepository.addBank(bank).onEach { state ->
                _res.value = state
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