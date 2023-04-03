package com.advancedfinance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.advancedfinance.databinding.AppActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: AppActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AppActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}