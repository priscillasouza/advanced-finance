package com.advancedfinance.framework.infrastruture.local.database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val value: String,
    val description: String,
    val date: String,
    @ColumnInfo(name = "fk_category_id")
    val fkCategoryId: Int,
    @ColumnInfo(name = "fk_account_id")
    val fkAccountId: Int,
    val observation: String,
    @ColumnInfo(name = "is_received")
    val isReceived: Boolean,
    @ColumnInfo(name = "is_installments")
    val isInstallments: Boolean,
    @ColumnInfo(name = "is_fixed_value")
    val isFixedValue: Boolean,
    @ColumnInfo(name = "is_pay_in_installments")
    val isPayInInstallments: Boolean,
    val repetitions: String,
    @ColumnInfo(name = "fk_period_type_id")
    val fkPeriodTypeId: Int,
    @ColumnInfo(name = "transaction_type")
    val transactionType: Int
)