package com.advancedfinance.account_finance.presentation.model

data class AccountCategoryModel(
    val id: Int,
    val name: String,
    var selected: Boolean = false
)