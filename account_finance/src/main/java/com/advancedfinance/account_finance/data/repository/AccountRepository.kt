package com.advancedfinance.account_finance.data.repository

import com.advancedfinance.account_finance.data.mapper.MapEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapModelToEntity
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO

class AccountRepository(
    private var accountDAO: AccountDAO,
    private var mapModelToEntity: MapModelToEntity
): IAccountRepository {

    override suspend fun addAccount(accountModel: AccountModel) {
        mapModelToEntity.transform(accountModel).also {
            accountDAO.addAccount(it)
        }
    }
}