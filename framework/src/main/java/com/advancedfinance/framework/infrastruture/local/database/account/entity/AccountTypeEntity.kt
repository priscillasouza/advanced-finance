package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_account_type")
data class AccountTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)