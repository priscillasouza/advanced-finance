package com.advancedfinance.category.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionType (
    val id: Int,
    val name: String
):Parcelable