package com.advancedfinance.framework.infrastruture.local.database.periodtype.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "period_type")
data class PeriodTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)