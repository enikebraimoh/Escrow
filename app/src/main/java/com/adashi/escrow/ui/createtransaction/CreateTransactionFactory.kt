package com.adashi.escrow.ui.createtransaction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository

class CreateTransactionFactory (
    val app : Application,
    val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  CreateTransactionViewModel(app, homeRepository) as T
    }
}