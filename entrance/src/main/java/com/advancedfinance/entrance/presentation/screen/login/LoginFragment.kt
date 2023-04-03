package com.advancedfinance.entrance.presentation.screen.login

import android.os.Bundle
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.entrance.databinding.EntranceFragmentLoginBinding

class LoginFragment : BaseFragment<EntranceFragmentLoginBinding, LoginViewModel>(
    EntranceFragmentLoginBinding::inflate,
    LoginViewModel::class
) {
    override fun prepareView(savedInstanceState: Bundle?) {}
}