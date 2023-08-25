package com.advancedfinance.account_finance.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class AccountModel(
    val id: Int?,
    val name: String,
    val startedBalance: BigDecimal,
    val accountType: AccountTypeModel
):Parcelable