package com.advancedfinance.framework.infrastruture.local.database.transaction.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity

data class TransactionWithAllRelations(
    @Embedded val transactionEntity: TransactionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val transactionTypeEntity: TransactionTypeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val categoryEntity: List<CategoryEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val accountEntity: List<AccountEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val accountTypeEntity: List<AccountTypeEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val periodTypeEntity: List<PeriodTypeEntity>,
)