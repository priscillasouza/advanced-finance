package com.advancedfinance.overview.domain.usecase

import com.advancedfinance.account_finance.data.repository.AccountRepository
import com.advancedfinance.core.domain.usecase.BaseUseCase
import com.advancedfinance.transaction.data.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class InitOverviewUseCase(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
): BaseUseCase<Any, String>() {

    override suspend fun buildUseCaseFlow(params: Any): Flow<Result<String>> {
        accountRepository.getAccounts()
        return TODO("Provide the return value")
    }

}