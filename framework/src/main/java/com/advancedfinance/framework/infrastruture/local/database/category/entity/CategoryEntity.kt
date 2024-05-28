package com.advancedfinance.framework.infrastruture.local.database.category.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "fk_transaction_type_id")
    val fkTransactionTypeId: Int
)