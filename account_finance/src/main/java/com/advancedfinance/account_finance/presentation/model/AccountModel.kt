package com.advancedfinance.account_finance.presentation.model

import java.math.BigDecimal

data class AccountModel(
    val id: Int,
    val name: String,
    val startedBalance: BigDecimal,
    val accountCategory: String
)