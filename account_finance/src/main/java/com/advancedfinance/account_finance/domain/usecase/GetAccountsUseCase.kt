package com.advancedfinance.account_finance.domain.usecase

import com.advancedfinance.account_finance.data.repository.AccountRepository
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetAccountsUseCase(
    private val accountRepository: IAccountRepository,
) : BaseUseCase<Any, AccountModel>() {
    override suspend fun buildUseCaseFlow(params: Any): Flow<Result<AccountModel>> {
        return flow {
            try {
                emit(Result.State.Loading)
                accountRepository.getAccounts().collect { accounts ->
                    accounts.let {
                        /*emit(Result.Success(it))*/
                    } ?: emit(Result.Empty)
                }
            } catch (e: Throwable) {
                emit(Result.Error(e))
            } finally {
                emit(Result.State.Loaded)
            }
        }
    }


}