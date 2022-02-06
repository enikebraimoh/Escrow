package com.adashi.escrow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

@HiltAndroidApp
class EscrowApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}