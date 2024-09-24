package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.core.data.IMapper
import com.advancedfinance.core.extensions.orZero
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity
import com.advancedfinance.transaction.presentation.model.TransactionModel

class MapModelToEntity : IMapper<TransactionModel, TransactionEntity> {

    override fun transform(transaction: TransactionModel): TransactionEntity {
        return TransactionEntity(
            id = transaction.id,
            value = transaction.value.toString(),
            description = transaction.description,
            date = transaction.date,
            fkCategoryId = transaction.category?.id.orZero(),
            observation = transaction.observation,
            isReceived = transaction.isReceived,
            isInstallments = transaction.isInstallments,
            isFixedValue = transaction.isFixedValue,
            isPayInInstallments = transaction.isPayInInstallments,
            repetitions = transaction.repetitions,
            fkAccountId = transaction.account?.accountType?.id.orZero(),
            fkPeriodTypeId = transaction.period?.id.orZero(),
            transactionType = transaction.transactionType.id
        )
    }
}