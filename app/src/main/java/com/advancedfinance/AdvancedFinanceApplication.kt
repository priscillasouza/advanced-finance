package com.advancedfinance

import android.app.Application
import com.advancedfinance.account_finance.di.accountModule
import com.advancedfinance.category.di.categoryModule
import com.advancedfinance.entrance.di.entranceModule
import com.advancedfinance.framework.di.apiModule
import com.advancedfinance.framework.di.daoModule
import com.advancedfinance.framework.di.frameworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AdvancedFinanceApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@AdvancedFinanceApplication)
            modules(
                daoModule,
                apiModule,
                frameworkModule,
                entranceModule,
                accountModule,
                categoryModule
            )
        }
    }
}