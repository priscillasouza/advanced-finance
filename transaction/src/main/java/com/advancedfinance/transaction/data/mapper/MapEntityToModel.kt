package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.core.extensions.orZero
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionWithAllRelations
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import java.math.BigDecimal

class MapEntityToModel : IMapper<TransactionWithAllRelations, TransactionModel> {

    override fun transform(entity: TransactionWithAllRelations): TransactionModel {
        return TransactionModel(
            id = entity.transactionEntity.id,
            value = entity.transactionEntity.value.toBigDecimal(),
            description = entity.transactionEntity.description,
            date = entity.transactionEntity.date,
            category = CategoryModel(
                id = entity.categoryEntity?.category?.id,
                name = entity.categoryEntity?.category?.name.orEmpty(),
                transactionType = TransactionType(
                    id = entity.transactionTypeEntity?.id.orZero(),
                    name = entity.transactionTypeEntity?.name.orEmpty())
            ),
            observation = entity.transactionEntity.observation,
            isReceived = entity.transactionEntity.isReceived,
            isInstallments = entity.transactionEntity.isInstallments,
            isFixedValue = entity.transactionEntity.isFixedValue,
            isPayInInstallments = entity.transactionEntity.isPayInInstallments,
            repetitions = entity.transactionEntity.repetitions,
            account = AccountModel(
                id = entity.accountEntity?.id,
                name = entity.accountEntity?.name.orEmpty(),
                startedBalance = BigDecimal(0.0),
                accountType = AccountTypeModel(
                    id = entity.accountEntity?.accountType,
                    name = entity.accountEntity?.name.orEmpty()
                )
            ),
            period = PeriodTypeModel(
                id = entity.periodTypeEntity?.id.orZero(),
                name = entity.periodTypeEntity?.name.orEmpty()
            ),
            transactionType = TransactionType(
                id = entity.transactionTypeEntity?.id.orZero(),
                name = entity.transactionTypeEntity?.name.orEmpty()
            )
        )
    }
}