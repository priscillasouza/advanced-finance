package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionWithAllRelations
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import java.math.BigDecimal

class MapTransactionAllRelationsEntityToModel :
    IMapper<TransactionWithAllRelations, TransactionModel> {

    override fun transform(entity: TransactionWithAllRelations): TransactionModel {
        return TransactionModel(
            id = entity.transactionEntity.id,
            value = entity.transactionEntity.value.toBigDecimal(),
            description = entity.transactionEntity.description,
            date = entity.transactionEntity.date,
            category = entity.categoryEntity.run {
                CategoryModel(
                    id = entity.categoryEntity?.id ?: 0,
                    name = entity.categoryEntity?.name ?: "",
                    transactionType = TransactionType(
                        id = entity.transactionTypeEntity?.id ?: 0,
                        name = entity.transactionTypeEntity?.name ?: ""
                    )
                )
            },
            account = entity.accountEntity.run {
                AccountModel(
                    id = entity.accountEntity?.id ?: 0,
                    name = entity.accountEntity?.name ?: "",
                    startedBalance = entity.accountEntity?.startedBalance
                        ?: BigDecimal.ZERO,
                    accountType = AccountTypeModel(
                        id = entity.accountTypeEntity?.id ?: 0,
                        name = entity.accountTypeEntity?.name ?: ""
                    )
                )
            },
            observation = entity.transactionEntity.observation,
            isReceived = entity.transactionEntity.isReceived,
            isInstallments = entity.transactionEntity.isInstallments,
            isFixedValue = entity.transactionEntity.isFixedValue,
            isPayInInstallments = entity.transactionEntity.isPayInInstallments,
            repetitions = entity.transactionEntity.repetitions,
            period = entity.periodTypeEntity.run {
                PeriodTypeModel(
                    id = entity.periodTypeEntity.id ?: 0,
                    name = entity.periodTypeEntity.name ?: ""
                )
            },
            transactionType = entity.transactionTypeEntity.run {
                TransactionType(
                    id = entity.transactionTypeEntity.id,
                    name = entity.transactionTypeEntity.name
                )
            }
        )
    }
}