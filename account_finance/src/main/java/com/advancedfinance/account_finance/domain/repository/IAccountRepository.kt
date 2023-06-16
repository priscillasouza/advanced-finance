package com.advancedfinance.account_finance.domain.repository

import com.advancedfinance.account_finance.presentation.model.AccountModel
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    suspend fun saveAccount(accountModel: AccountModel)

    suspend fun updateAccount(accountModel: AccountModel)

    suspend fun deleteAccount(accountModel: AccountModel)

    fun getAccounts(): Flow<List<AccountModel>>

}