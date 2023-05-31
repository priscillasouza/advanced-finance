package com.advancedfinance.account_finance.domain.repository

import com.advancedfinance.account_finance.presentation.model.AccountModel

interface IAccountRepository {

    suspend fun addAccount(accountModel: AccountModel)

}