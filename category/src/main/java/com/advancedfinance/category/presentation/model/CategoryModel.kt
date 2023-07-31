package com.advancedfinance.category.presentation.model

data class CategoryModel(
    val id: Int?,
    val name: String,
    var transactionType : TransactionType
)