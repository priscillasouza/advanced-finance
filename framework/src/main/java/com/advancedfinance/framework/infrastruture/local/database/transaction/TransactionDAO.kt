package com.advancedfinance.framework.infrastruture.local.database.transaction

import androidx.room.*
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TransactionDAO {

    fun addTransaction(
        transactionEntity: TransactionEntity,
        categoryEntity: CategoryEntity,
        accountEntity: AccountEntity,
        periodTypeEntity: PeriodTypeEntity

        ) {
        transactionEntity.copy(
            fkCategoryId = categoryEntity.id,
            fkAccountId = accountEntity.id,
            fkPeriodTypeId = periodTypeEntity.id).let {
            addTransaction(it)
        }
    }

    fun updateTransaction(
        transactionEntity: TransactionEntity,
        categoryEntity: CategoryEntity,
        accountEntity: AccountEntity,
        periodTypeEntity: PeriodTypeEntity
    ) {
        transactionEntity.copy(
            fkCategoryId = categoryEntity.id,
            fkAccountId = accountEntity.id,
            fkPeriodTypeId = periodTypeEntity.id).let {
            updateTransaction(it)
        }
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addTransaction(transactionEntity: TransactionEntity)

    @Update
    abstract fun updateTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM period_type")
    abstract fun getAllPeriodType(): Flow<List<PeriodTypeEntity>>

    @Query("SELECT * FROM transaction_type WHERE id = :id")
    abstract fun getTransactionTypeById(id: Int): TransactionTypeEntity
}