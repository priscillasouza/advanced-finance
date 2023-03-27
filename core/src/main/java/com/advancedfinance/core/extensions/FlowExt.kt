package com.advancedfinance.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.onError(function: (Throwable)-> Unit): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: Throwable) {
        function(e)
    }
}