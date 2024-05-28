package com.advancedfinance.framework.infrastruture.local.database.account.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithAccountType(
    @Embedded val account: AccountEntity?,
    @Relation(
        parentColumn = "fk_account_type_id",
        entityColumn = "id",
        entity = AccountTypeEntity::class
    )
    val accountType: AccountTypeEntity?
)