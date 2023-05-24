package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val createdAt: String,
    val startedBalance: BigDecimal,
    val accountLimit: BigDecimal,
    val accountDefault: Boolean,
    val accountCategory: String,
    val showSummaryScreen: Boolean
)
