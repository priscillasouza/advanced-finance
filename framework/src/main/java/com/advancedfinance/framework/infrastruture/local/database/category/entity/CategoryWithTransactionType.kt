package com.advancedfinance.framework.infrastruture.local.database.category.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithTransactionType(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val transactionType: List<TransactionTypeEntity>
)