package com.advancedfinance.account_finance.di

import com.advancedfinance.account_finance.data.mapper.MapAccountTypeEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapModelToEntity
import com.advancedfinance.account_finance.data.repository.AccountRepository
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.screen.account.AccountViewModel
import com.advancedfinance.account_finance.presentation.screen.account_list.AccountListViewModel
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val accountModule = module {

    viewModel {
        AccountViewModel(get(named("AccountRepository")))
    }

    viewModel {
        AccountListViewModel(get(named("AccountRepository")))
    }

    single<IAccountRepository>(named("AccountRepository")) {
        AccountRepository(get<AccountDAO>(), get(), get(), get())
    }

    single { MapModelToEntity() }

    single { MapEntityToModel() }

    single { MapAccountTypeEntityToModel() }
}
