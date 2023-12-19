package com.advancedfinance.transaction.di

import com.advancedfinance.account_finance.data.mapper.MapAccountTypeEntityToModel
import com.advancedfinance.framework.infrastruture.local.database.transaction.TransactionDAO
import com.advancedfinance.transaction.data.mapper.MapEntityToModel
import com.advancedfinance.transaction.data.mapper.MapModelToEntity
import com.advancedfinance.transaction.data.mapper.MapPeriodTypeEntityToModel
import com.advancedfinance.transaction.data.mapper.MapTransactionAllRelationsEntityToModel
import com.advancedfinance.transaction.data.mapper.MapTransactionTypeEntityToModel
import com.advancedfinance.transaction.data.repository.TransactionRepository
import com.advancedfinance.transaction.domain.repository.ITransactionRepository
import com.advancedfinance.transaction.presentation.screen.transaction.TransactionViewModel
import com.advancedfinance.transaction.presentation.screen.transaction_list.TransactionListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val transactionModule = module {

    viewModel {
        TransactionViewModel(get(named("TransactionRepository")),
            get(named("CategoryRepository")),
            get(named("AccountRepository")))
    }

    viewModel {
        TransactionListViewModel(get(named("TransactionRepository")))
    }

    single<ITransactionRepository>(named("TransactionRepository")) {
        TransactionRepository(get<TransactionDAO>(), get(), get(), get(), get(),get())
    }

    single { MapModelToEntity() }
    single { MapEntityToModel() }
    single { MapTransactionAllRelationsEntityToModel() }
    single { MapAccountTypeEntityToModel() }
    single { MapPeriodTypeEntityToModel() }
    single { MapTransactionTypeEntityToModel() }

}