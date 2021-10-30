package com.adashi.escrow.ui.transactions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository

class TransactionsFactory (
    val app : Application,
    val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  TransactionsViewModel(app, homeRepository) as T
    }
}