package com.advancedfinance.account_finance.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountWithAccountType

class MapEntityToModel : IMapper<AccountWithAccountType, AccountModel> {

    override fun transform(entity: AccountWithAccountType): AccountModel {
        return AccountModel(
            id = entity.account.id,
            name = entity.account.name,
            startedBalance = entity.account.startedBalance,
            accountType = entity.accountType.run {
                AccountTypeModel(
                    id = entity.account.id,
                    name = entity.account.name
                )
            }
        )
    }
}