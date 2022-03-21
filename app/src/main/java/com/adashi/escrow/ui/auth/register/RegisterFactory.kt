package com.adashi.escrow.ui.auth.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adashi.escrow.repository.AuthRepository

class RegisterFactory (
    val app : Application,
    val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(app, authRepository) as T
    }
}