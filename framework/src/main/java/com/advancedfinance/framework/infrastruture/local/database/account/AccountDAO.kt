package com.advancedfinance.framework.infrastruture.local.database.account

import androidx.room.*
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
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
    fun getAccounts(): Flow<List<AccountEntity>>

}