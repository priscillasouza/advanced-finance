package com.advancedfinance.account_finance.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AccountTypeModel(
    val id: Int,
    val name: String
):Parcelable