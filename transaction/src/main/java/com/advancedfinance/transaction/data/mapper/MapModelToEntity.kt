package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity
import com.advancedfinance.transaction.presentation.model.TransactionModel

class MapModelToEntity : IMapper<TransactionModel, TransactionEntity> {

    override fun transform(transaction: TransactionModel): TransactionEntity {
        return TransactionEntity(
            id = transaction.id,
            value = transaction.value.toString(),
            description = transaction.description,
            date = transaction.date,
            fkCategoryId = transaction.category?.id ?: 0,
            observation = transaction.observation,
            isReceived = transaction.isReceived,
            isInstallments = transaction.isInstallments,
            isFixedValue = transaction.isFixedValue,
            isPayInInstallments = transaction.isPayInInstallments,
            repetitions = transaction.repetitions,
            fkAccountId = transaction.account?.accountType?.id ?: 0,
            fkPeriodTypeId = transaction.period?.id ?: 0,
            transactionType = transaction.transactionType.id
        )
    }
}