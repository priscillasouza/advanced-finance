package com.advancedfinance.framework.infrastruture.local.database.transaction

import androidx.room.*
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionWithAllRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

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
    fun addTransaction(transactionEntity: TransactionEntity)

    @Update
    fun updateTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM period_type")
   fun getAllPeriodType(): Flow<List<PeriodTypeEntity>>

    @Query("SELECT * FROM transaction_type WHERE id = :id")
    fun getTransactionTypeById(id: Int): TransactionTypeEntity

    /*@Transaction
    @Query("SELECT * FROM `transaction`  " +
            "JOIN TRANSACTION_TYPE ON  `transaction`.id_transaction = TRANSACTION_TYPE.id_transaction_type " +
            "JOIN CATEGORY ON `transaction`.id_transaction = CATEGORY.id_category " +
            "JOIN ACCOUNT ON `transaction`.id_transaction = ACCOUNT.id_account " +
            "JOIN CATEGORY_ACCOUNT_TYPE ON `transaction`.id_transaction = CATEGORY_ACCOUNT_TYPE.id_account_type " +
            "JOIN PERIOD_TYPE ON `transaction`.id_transaction = PERIOD_TYPE.id_period_type "
    )

    fun getAllTransaction(): Flow<List<TransactionEntity>>*/

    @Transaction
    @Query("SELECT * FROM `transaction` WHERE id_transaction = :id")
    fun getAllTransaction(id: Int): Flow<List<TransactionWithAllRelations>>

}