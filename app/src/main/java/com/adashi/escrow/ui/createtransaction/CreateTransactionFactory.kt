package com.adashi.escrow.ui.createtransaction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.AuthRepository

class CreateTransactionFactory (
    val app : Application,
    val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  CreateTransactionViewModel(app, authRepository) as T
    }
}