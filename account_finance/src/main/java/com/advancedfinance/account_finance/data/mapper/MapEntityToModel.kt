package com.advancedfinance.account_finance.data.mapper

import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.core.extensions.orNegative
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountWithAccountType
import java.math.BigDecimal

class MapEntityToModel : IMapper<AccountWithAccountType, AccountModel> {

    override fun transform(entity: AccountWithAccountType): AccountModel {
        return AccountModel(
            id = entity.account?.id,
            name = entity.account?.name.orEmpty(),
            startedBalance = entity.account?.startedBalance?: BigDecimal(0),
            accountType = entity.accountType.run {
                AccountTypeModel(
                    id = entity.account?.id.orNegative(),
                    name = entity.account?.name.orEmpty()
                )
            }
        )
    }
}