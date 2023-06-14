package com.advancedfinance.account_finance.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity

class MapModelToEntity : IMapper<AccountModel, AccountEntity> {

    override fun transform(account: AccountModel): AccountEntity {
        return AccountEntity(
            id = account.id?: 0,
            name = account.name,
            startedBalance = account.startedBalance,
            accountCategory = account.accountCategory
        )
    }
}