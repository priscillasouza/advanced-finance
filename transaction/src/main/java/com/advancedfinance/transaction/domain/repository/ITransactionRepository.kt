package com.advancedfinance.transaction.domain.repository

import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    suspend fun saveTransaction(transactionModel: TransactionModel)

    suspend fun updateTransaction(transactionModel: TransactionModel)

    fun getAllPeriodType(): Flow<List<PeriodTypeModel>>

    fun getTransactionTypeById(id: Int): TransactionType
}