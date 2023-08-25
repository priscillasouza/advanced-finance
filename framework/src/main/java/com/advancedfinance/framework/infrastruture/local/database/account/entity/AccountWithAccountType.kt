package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithAccountType(
    @Embedded val account: AccountEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val accountType: List<AccountTypeEntity>
)