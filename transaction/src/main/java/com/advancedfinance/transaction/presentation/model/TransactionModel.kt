package com.advancedfinance.transaction.presentation.model

import android.os.Parcelable
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class TransactionModel(
    val id: Int?,
    val transactionType: TransactionType,
    val value: BigDecimal,
    val description: String,
    val date: String,
    val category: CategoryModel?,
    val account: AccountModel?,
    val observation: String,
    var isReceived: Boolean,
    val isInstallments: Boolean,
    val isFixedValue: Boolean,
    val isPayInInstallments: Boolean,
    val repetitions: String,
    val period: PeriodTypeModel?
) : Parcelable