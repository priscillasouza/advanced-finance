package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.transaction.entity.TransactionEntity
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import java.math.BigDecimal

class MapEntityToModel : IMapper<TransactionEntity, TransactionModel> {

    override fun transform(entity: TransactionEntity): TransactionModel {
        return TransactionModel(
            id = entity.id,
            value = entity.value.toBigDecimal(),
            description = entity.description,
            date = entity.date,
            category = CategoryModel(
                id = entity.fkCategoryId,
                name = entity.description,
                transactionType = TransactionType(id = 0, name = "Nao sei")
            ),
            observation = entity.observation,
            isReceived = entity.isReceived,
            isInstallments = entity.isInstallments,
            isFixedValue = entity.isFixedValue,
            isPayInInstallments = entity.isPayInInstallments,
            repetitions = entity.repetitions,
            account = AccountModel(
                id = entity.fkAccountId,
                name = "",
                startedBalance = BigDecimal(0.0),
                accountType = AccountTypeModel(
                    id = 0,
                    name = ""
                )
            ),
            period = PeriodTypeModel(
                id = entity.fkPeriodTypeId,
                name = ""
            ),
            transactionType = TransactionType(
                id = entity.transactionType,
                name = ""
            )
        )
    }
}