package com.advancedfinance

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
                com.advancedfinance.overview.R.id.overview_navigation,
                com.advancedfinance.account_finance.R.id.account_finance_navigation,
                com.advancedfinance.category.R.id.category_navigation,
                com.advancedfinance.transaction.R.id.transaction_navigation
            ), drawerLayout)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.advancedfinance.account_finance.R.id.account_finance_accountfragment -> hideAppBarDrawer()
                com.advancedfinance.account_finance.R.id.account_finance_accountlistfragment -> showAppBarDrawer()
                com.advancedfinance.category.R.id.category_categoryfragment -> hideAppBarDrawer()
                com.advancedfinance.category.R.id.category_categorylistfragment -> showAppBarDrawer()
                com.advancedfinance.transaction.R.id.transaction_transactionfragment -> hideAppBarDrawer()
                com.advancedfinance.overview.R.id.overview_transactionfragment -> hideAppBarDrawer()
                com.advancedfinance.transaction.R.id.transaction_transactionlistfragment -> {
                    showAppBarDrawer()
                    statusBarColor()
                }
                com.advancedfinance.overview.R.id.overview_overviewfragment -> {
                    showAppBarDrawer()
                    statusBarColor()
                }
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun statusBarColor() {
        window.statusBarColor = ContextCompat.getColor(this, com.advancedfinance.core.R.color.core_md_theme_light_primary)
    }

    private fun showAppBarDrawer() {
        binding.appBarDrawer.toolbar.visibility = View.VISIBLE
    }

    private fun hideAppBarDrawer() {
        binding.appBarDrawer.toolbar.visibility = View.GONE
    }
}