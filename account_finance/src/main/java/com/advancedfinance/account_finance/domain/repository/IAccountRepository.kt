package com.advancedfinance.account_finance.domain.repository

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {

    suspend fun saveAccount(accountModel: AccountModel)

    suspend fun updateAccount(accountModel: AccountModel)

    suspend fun deleteAccount(accountModel: AccountModel)

    fun getAccounts(): Flow<List<AccountModel>>

    fun getAllAccountType(): Flow<List<AccountTypeModel>>
}