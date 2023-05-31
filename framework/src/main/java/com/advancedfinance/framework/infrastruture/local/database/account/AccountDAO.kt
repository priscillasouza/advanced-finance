package com.advancedfinance.framework.infrastruture.local.database.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.advancedfinance.framework.infrastruture.local.database.account.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAccount(accountEntity: AccountEntity)
}