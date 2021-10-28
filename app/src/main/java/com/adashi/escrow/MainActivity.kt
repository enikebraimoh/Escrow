package com.adashi.escrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.adashi.escrow.databinding.ActivityMainBinding
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val session = SessionManager(this)
        App.token = session.fetchAuthToken()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

    }

}