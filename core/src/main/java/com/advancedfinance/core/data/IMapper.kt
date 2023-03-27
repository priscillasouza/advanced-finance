package com.advancedfinance.core.data

interface IMapper<I, O> {
    fun transform(entity: I): O
    fun transform(list: List<I>): List<O> = list.map { transform(it) }
}