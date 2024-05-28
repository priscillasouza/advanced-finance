package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.core.extensions.orNegative
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
            category = entity.categoryEntity?.run {
                CategoryModel(
                    id = category?.id.orNegative(),
                    name = category?.name.orEmpty(),
                    transactionType = TransactionType(
                        id = transactionType?.id.orNegative(),
                        name = transactionType?.name.orEmpty()
                    )
                )
            },
            account = entity.accountEntity.run {
                AccountModel(
                    id = entity.accountEntity?.id.orNegative(),
                    name = entity.accountEntity?.name.orEmpty(),
                    startedBalance = entity.accountEntity?.startedBalance?: BigDecimal(0),
                    accountType = AccountTypeModel(
                        id = this?.id.orNegative(),
                        name = this?.name.orEmpty()
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
                    id = entity.periodTypeEntity?.id.orNegative(),
                    name = entity.periodTypeEntity?.name.orEmpty()
                )
            },
            transactionType = entity.transactionTypeEntity.run {
                TransactionType(
                    id = entity.transactionTypeEntity?.id.orNegative(),
                    name = entity.transactionTypeEntity?.name.orEmpty()
                )
            }
        )
    }
}