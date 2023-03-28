package com.advancedfinance.core.domain.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<in Params, out TypeReturn> constructor(
    protected val backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineScope {

    protected abstract suspend fun buildUseCaseFlow(params: Params): Flow<Result<TypeReturn>>

    suspend fun execute(params: Params): Flow<Result<TypeReturn>> {
        return withContext(backgroundDispatcher) {
            buildUseCaseFlow(params)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = backgroundDispatcher + Job()

    sealed class Result<out T> {
        data class Success<out T>(val result: T): Result<T>()
        object Empty: Result<Nothing>()
        data class Error(val throwable: Throwable): Result<Nothing>()
    }

}