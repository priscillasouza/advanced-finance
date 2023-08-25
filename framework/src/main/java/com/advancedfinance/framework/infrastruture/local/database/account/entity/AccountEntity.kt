package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "started_balance")
    val startedBalance: BigDecimal,
    @ColumnInfo(name = "account_type")
    val accountType: String
)
