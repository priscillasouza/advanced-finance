package com.advancedfinance.overview.di

import com.advancedfinance.overview.presentation.screen.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val overviewModule = module {

    viewModel {
        OverviewViewModel()
    }
}