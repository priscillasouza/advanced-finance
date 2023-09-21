package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.category.entity.TransactionTypeEntity

class MapTransactionTypeEntityToModel : IMapper<TransactionTypeEntity, TransactionType> {

    override fun transform(transactionType: TransactionTypeEntity): TransactionType {
        return TransactionType(
            id = transactionType.id,
            name = transactionType.name
        )
    }
}