package com.advancedfinance.transaction.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeriodTypeModel(
    val id: Int,
    val name: String
): Parcelable