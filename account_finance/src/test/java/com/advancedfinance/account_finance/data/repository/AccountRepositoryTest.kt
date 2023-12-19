package com.advancedfinance.account_finance.data.repository

import com.advancedfinance.account_finance.data.mapper.MapAccountTypeEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapEntityToModel
import com.advancedfinance.account_finance.data.mapper.MapModelToEntity
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.account.AccountDAO
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountWithAccountType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class AccountRepositoryTest {

    @Test
    fun `deve chamar o dao quando salva uma conta`() = runTest  {
        //Configuração
        val dao = mockk<AccountDAO>()
        val mapModelToEntity = MapModelToEntity()
        val mapEntityToModel = MapEntityToModel()
        val mapAccountTypeEntityToModel = MapAccountTypeEntityToModel()
        val accountRepository = AccountRepository(dao, mapModelToEntity, mapEntityToModel, mapAccountTypeEntityToModel)

        val account = AccountModel(
            id = 0,
            name = "Dinheiro",
            startedBalance = BigDecimal(12.66),
            accountType = AccountTypeModel(id = 1, name = "Receita")
        )

        val accountEntity = AccountEntity(
            id = 0,
            name = "Dinheiro",
            startedBalance = BigDecimal(12.66),
            accountType = "Receita"
        )

        //Toda vez que o dao.addAccount for chamado, nós queremos retornar o que o dao.addAccount retorna
        coEvery {
            dao.addAccount(accountEntity)
        }.returns(Unit)


        //Ação
        accountRepository.saveAccount(account)

        coVerify {
            dao.addAccount(accountEntity)
        }
    }

    @Test
    fun `deve chamar o dao quando atualizar uma conta`() = runTest  {
        //Configuração
        val dao = mockk<AccountDAO>()
        val mapModelToEntity = MapModelToEntity()
        val mapEntityToModel = MapEntityToModel()
        val mapAccountTypeEntityToModel = MapAccountTypeEntityToModel()
        val accountRepository = AccountRepository(dao, mapModelToEntity, mapEntityToModel, mapAccountTypeEntityToModel)

        val account = AccountModel(
            id = 0,
            name = "Conta",
            startedBalance = BigDecimal(14.99),
            accountType = AccountTypeModel(id = 2, name = "Despesa")
        )

        val accountEntity = AccountEntity(
            id = 0,
            name = "Conta",
            startedBalance = BigDecimal(14.99),
            accountType = "Despesa"
        )


        coEvery {
            dao.updateAccount(accountEntity)
        }.returns(Unit)


        //Ação
        accountRepository.updateAccount(account)

        coVerify {
            dao.updateAccount(accountEntity)
        }
    }

    @Test
    fun `deve chamar o dao quando deletar uma conta`() = runTest  {
        //Configuração
        val dao = mockk<AccountDAO>()
        val mapModelToEntity = MapModelToEntity()
        val mapEntityToModel = MapEntityToModel()
        val mapAccountTypeEntityToModel = MapAccountTypeEntityToModel()
        val accountRepository = AccountRepository(dao, mapModelToEntity, mapEntityToModel, mapAccountTypeEntityToModel)

        val account = AccountModel(
            id = 0,
            name = "Conta",
            startedBalance = BigDecimal(14.99),
            accountType = AccountTypeModel(id = 2, name = "Despesa")
        )

        val accountEntity = AccountEntity(
            id = 0,
            name = "Conta",
            startedBalance = BigDecimal(14.99),
            accountType = "Despesa"
        )


        coEvery {
            dao.deleteAccount(accountEntity)
        }.returns(Unit)


        //Ação
        accountRepository.deleteAccount(account)

        coVerify {
            dao.deleteAccount(accountEntity)
        }
    }
}