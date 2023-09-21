package com.advancedfinance.transaction.presentation.model

import android.os.Parcelable
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class TransactionModel(
    val id: Int? = null,
    val transactionType: TransactionType,
    val value: BigDecimal,
    val description: String,
    val date: String,
    val category: CategoryModel?,
    val account: AccountModel?,
    val observation: String,
    var isReceived: Boolean = false,
    val isInstallments: Boolean = false,
    val isFixedValue: Boolean = false,
    val isPayInInstallments: Boolean = false,
    val repetitions: String,
    val period: PeriodTypeModel?
) : Parcelable