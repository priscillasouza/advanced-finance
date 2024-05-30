package com.advancedfinance.framework.infrastruture.local.database.category.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithTransactionType(
    @Embedded val category: CategoryEntity?,
    @Relation(
        parentColumn = "fk_transaction_type_id",
        entityColumn = "id",
        entity = TransactionTypeEntity::class
    )
    val transactionType: TransactionTypeEntity?
)