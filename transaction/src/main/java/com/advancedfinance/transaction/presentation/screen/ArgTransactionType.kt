package com.advancedfinance.transaction.presentation.screen

import java.io.Serializable

enum class ArgTransactionType(val value: Int) : Serializable {
    Revenue(1),
    Expense(2);

    companion object ArgTransactionTypeCompanion : Serializable {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value }
    }
}