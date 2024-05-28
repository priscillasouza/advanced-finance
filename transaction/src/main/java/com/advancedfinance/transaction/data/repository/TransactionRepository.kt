package com.advancedfinance.transaction.data.repository

import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.framework.infrastruture.local.database.transaction.TransactionDAO
import com.advancedfinance.transaction.data.mapper.MapEntityToModel
import com.advancedfinance.transaction.data.mapper.MapModelToEntity
import com.advancedfinance.transaction.data.mapper.MapPeriodTypeEntityToModel
import com.advancedfinance.transaction.data.mapper.MapTransactionAllRelationsEntityToModel
import com.advancedfinance.transaction.data.mapper.MapTransactionTypeEntityToModel
import com.advancedfinance.transaction.domain.repository.ITransactionRepository
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(
    private var transactionDAO: TransactionDAO,
    private var mapModelToEntity: MapModelToEntity,
    private var mapEntityToModel: MapEntityToModel,
    private var mapPeriodTypeEntityToModel: MapPeriodTypeEntityToModel,
    private var mapTransactionTypeEntityToModel: MapTransactionTypeEntityToModel,
    private var mapTransactionAllRelationsEntityToModel: MapTransactionAllRelationsEntityToModel
) : ITransactionRepository {

    override suspend fun saveTransaction(transactionModel: TransactionModel) {
        mapModelToEntity.transform(transactionModel).also {
            transactionDAO.addTransaction(it)
        }
    }

    override suspend fun updateTransaction(transactionModel: TransactionModel) {
        mapModelToEntity.transform(transactionModel).also {
            transactionDAO.updateTransaction(it)
        }
    }

    override fun getAllPeriodType(): Flow<List<PeriodTypeModel>> {
        return transactionDAO.getAllPeriodType().map {
            mapPeriodTypeEntityToModel.transform(it)
        }
    }

    override fun getTransactionTypeById(id: Int): TransactionType {
        return transactionDAO.getTransactionTypeById(id).let {
            mapTransactionTypeEntityToModel.transform(it)
        }
    }

    override fun getAllTransaction(): Flow<List<TransactionModel>> {
        return transactionDAO.getAllTransaction().map {
            mapTransactionAllRelationsEntityToModel.transform(it)
        }
    }
}