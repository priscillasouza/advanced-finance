package com.advancedfinance.account_finance.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity

class MapEntityToModel: IMapper<AccountEntity, AccountModel> {

    override fun transform(entity: AccountEntity): AccountModel {
        return AccountModel(
            id = entity.id,
            name = entity.name,
            startedBalance = entity.startedBalance,
            accountCategory = entity.accountCategory
        )
    }
}