package com.advancedfinance.entrance.di

import com.advancedfinance.entrance.presentation.screen.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val entranceModule = module {

    viewModel {
        LoginViewModel(get())
    }

    single { "Hugo" }




}