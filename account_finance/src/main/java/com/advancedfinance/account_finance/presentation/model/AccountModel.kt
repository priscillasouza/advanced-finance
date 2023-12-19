package com.advancedfinance.account_finance.presentation.model

import android.os.Parcelable
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class AccountModel(
    val id: Int?,
    val name: String,
    val startedBalance: BigDecimal,
    val accountType: AccountTypeModel
):Parcelable {

    @Ignore
    val startedBalanceEhValido = !startedBalanceMenorOuIgualAZero() && !startedBalanceMaiorQueCem()

    private fun startedBalanceMenorOuIgualAZero(): Boolean {
        return startedBalance <= BigDecimal.ZERO
    }

    private fun startedBalanceMaiorQueCem(): Boolean {
        return startedBalance > BigDecimal(100)
    }

}

