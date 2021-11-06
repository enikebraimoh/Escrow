package com.adashi.escrow.ui.addbank.verifybvn.otp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.repository.SettingsRepository
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository

class VerifyOtpBnvFactory (
    val app : Application,
    val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  VerifyOtpBvnViewModel(app, settingsRepository) as T
    }
}