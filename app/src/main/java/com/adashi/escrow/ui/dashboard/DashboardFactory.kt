package com.adashi.escrow.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.HomeRepository

class DashboardFactory (
    val app : Application,
    val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  DashboardViewModel(app, homeRepository) as T
    }
}