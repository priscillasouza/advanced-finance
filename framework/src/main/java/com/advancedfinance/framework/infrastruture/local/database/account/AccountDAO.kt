package com.advancedfinance.framework.infrastruture.local.database.account

import androidx.room.*
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountTypeEntity
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountWithAccountType
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAccount(accountEntity: AccountEntity)

    @Update
    fun updateAccount(accountEntity: AccountEntity)

    @Delete
    fun deleteAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM account")
    fun getAccounts(): Flow<List<AccountWithAccountType>>

    @Query("SELECT * FROM category_account_type")
    fun getAllAccountType(): Flow<List<AccountTypeEntity>>
}