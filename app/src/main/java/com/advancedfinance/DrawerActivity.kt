package com.advancedfinance

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.advancedfinance.databinding.AppActivityDrawerBinding
import com.google.android.material.navigation.NavigationView

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: AppActivityDrawerBinding

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AppActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureNavigationDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configureNavigationDrawer() {
        setSupportActionBar(binding.appBarDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.category_navigation
            ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}