package com.advancedfinance.account_finance.domain.repository

import com.advancedfinance.account_finance.presentation.model.AccountModel
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    suspend fun addAccount(accountModel: AccountModel)

    fun getAccounts(): Flow<List<AccountModel>>
}