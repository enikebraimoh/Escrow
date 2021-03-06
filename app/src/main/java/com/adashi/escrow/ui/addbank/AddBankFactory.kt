package com.adashi.escrow.ui.addbank

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.repository.SettingsRepository

class AddBankFactory (
    val app : Application,
    val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  AddBankViewModel(app, settingsRepository) as T
    }
}