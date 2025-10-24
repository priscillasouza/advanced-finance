package com.advancedfinance.account_finance.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AccountTypeModel(
    val id: Int? = null,
    val name: String
):Parcelable {
    override fun toString(): String = name
    fun toInt(): Int? = id
}