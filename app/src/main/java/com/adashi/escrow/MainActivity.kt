package com.adashi.escrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adashi.escrow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.network.SessionManager
import ng.adashi.utils.App

@AndroidEntryPoint
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
        setupNavigation()
        binding.bottomNavigation.setupWithNavController(navController)

    }

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.transactionsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.productsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.settingsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}