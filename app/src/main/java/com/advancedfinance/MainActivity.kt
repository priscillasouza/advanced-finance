package com.advancedfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.advancedfinance.entrance.presentation.screen.login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)

        val container = findViewById<FrameLayout>(R.id.container_fragment)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = LoginFragment()

        fragmentTransaction.add(R.id.container_fragment, fragment)
        fragmentTransaction.commit()
    }
}