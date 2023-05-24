package com.advancedfinance.framework.di

import androidx.room.Room
import com.advancedfinance.framework.infrastruture.local.database.AdvancedFinanceDatabase
import com.advancedfinance.framework.infrastruture.local.database.DB_NAME
import org.koin.dsl.module

val daoModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AdvancedFinanceDatabase::class.java,
            DB_NAME
        ).allowMainThreadQueries()
            .build()
    }
    single { get<AdvancedFinanceDatabase>().accountDao() }
}