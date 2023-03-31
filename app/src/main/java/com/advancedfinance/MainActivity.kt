package com.advancedfinance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.advancedfinance.databinding.AppActivityMainBinding
import com.advancedfinance.entrance.presentation.screen.login.LoginFragment

private const val REQ_ONE_TAP = 2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: AppActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = LoginFragment()

        fragmentTransaction.add(binding.appContainerFragment.id, fragment)
        fragmentTransaction.commit()
    }
}