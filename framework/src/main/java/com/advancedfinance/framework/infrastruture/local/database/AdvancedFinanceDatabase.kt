package com.advancedfinance.framework.infrastruture.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity

const val DB_NAME = "account_db"

@Database(
    entities = [AccountEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(BigDecimalConverter::class)
abstract class AdvancedFinanceDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDAO
}