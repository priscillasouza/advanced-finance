package com.advancedfinance.category.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val id: Int?,
    val name: String,
    var transactionType : TransactionType
):Parcelable