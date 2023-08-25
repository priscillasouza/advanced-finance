package com.advancedfinance.account_finance.data.repository

import com.advancedfinance.account_finance.data.mapper.MapAccountTypeEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapModelToEntity
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepository(
    private var accountDAO: AccountDAO,
    private var mapModelToEntity: MapModelToEntity,
    private var mapEntityToModel: MapEntityToModel,
    private var mapAccountTypeEntityToModel: MapAccountTypeEntityToModel,
) : IAccountRepository {

    override suspend fun saveAccount(accountModel: AccountModel) {
        mapModelToEntity.transform(accountModel).also {
            accountDAO.addAccount(it)
        }
    }

    override suspend fun updateAccount(accountModel: AccountModel) {
        mapModelToEntity.transform(accountModel).also {
            accountDAO.updateAccount(it)
        }
    }

    override suspend fun deleteAccount(accountModel: AccountModel) {
        mapModelToEntity.transform(accountModel).also {
            accountDAO.deleteAccount(it)
        }
    }

    override fun getAccounts(): Flow<List<AccountModel>> {
        return accountDAO.getAccounts().map {
            mapEntityToModel.transform(it)
        }
    }

    override fun getAllAccountType(): Flow<List<AccountTypeModel>> {
        return accountDAO.getAllAccountType().map {
            mapAccountTypeEntityToModel.transform(it)
        }
    }
}