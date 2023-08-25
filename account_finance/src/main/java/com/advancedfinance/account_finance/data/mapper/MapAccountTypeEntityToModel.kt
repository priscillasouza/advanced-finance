package com.advancedfinance.account_finance.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountTypeEntity

class MapAccountTypeEntityToModel : IMapper<AccountTypeEntity, AccountTypeModel> {

    override fun transform(entity: AccountTypeEntity): AccountTypeModel {
        return AccountTypeModel(
            id = entity.id,
            name = entity.name
        )
    }
}