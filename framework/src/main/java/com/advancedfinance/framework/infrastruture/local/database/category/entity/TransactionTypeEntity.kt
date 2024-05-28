package com.advancedfinance.framework.infrastruture.local.database.category.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_type")
data class TransactionTypeEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)