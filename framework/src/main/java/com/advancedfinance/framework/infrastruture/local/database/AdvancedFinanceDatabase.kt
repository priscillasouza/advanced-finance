package com.advancedfinance.framework.infrastruture.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.category.CategoryDAO
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.transaction.TransactionDAO
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity

const val DB_NAME = "account_db"

@Database(
    entities = [
        AccountEntity::class,
        AccountTypeEntity::class,
        CategoryEntity::class,
        PeriodTypeEntity::class,
        TransactionEntity::class,
        TransactionTypeEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(BigDecimalConverter::class)
abstract class AdvancedFinanceDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDAO
    abstract fun categoryDao(): CategoryDAO
    abstract fun transactionDao(): TransactionDAO
}