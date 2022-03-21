package com.adashi.escrow.ui.settings.bankaccounts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.repository.SettingsRepository

class WithdrawalBankFactory (
    val app : Application,
    val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  WithdrawalBanksViewModel(app, settingsRepository) as T
    }
}