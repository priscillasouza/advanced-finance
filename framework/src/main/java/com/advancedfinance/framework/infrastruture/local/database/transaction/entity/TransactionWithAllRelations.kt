package com.advancedfinance.framework.infrastruture.local.database.transaction.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryWithTransactionType
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity

data class TransactionWithAllRelations(
    @Embedded val transactionEntity: TransactionEntity,
    @Relation(
        parentColumn = "transaction_type",
        entityColumn = "id",
        entity = TransactionTypeEntity::class
    )
    val transactionTypeEntity: TransactionTypeEntity?,

    @Relation(
        parentColumn = "fk_category_id",
        entityColumn = "id",
        entity = CategoryEntity::class
    )
    val categoryEntity: CategoryWithTransactionType?,

    @Relation(
        parentColumn = "fk_account_id",
        entityColumn = "id",
        entity = AccountEntity::class
    )
    val accountEntity: AccountEntity?,

    @Relation(
        parentColumn = "fk_period_type_id",
        entityColumn = "id",
        entity = PeriodTypeEntity::class
    )
    val periodTypeEntity: PeriodTypeEntity?
)