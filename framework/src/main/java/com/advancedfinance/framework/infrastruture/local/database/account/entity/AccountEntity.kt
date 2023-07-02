package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val startedBalance: BigDecimal,
    val accountCategory: String
)
