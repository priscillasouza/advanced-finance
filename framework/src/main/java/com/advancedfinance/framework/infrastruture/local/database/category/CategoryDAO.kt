package com.advancedfinance.framework.infrastruture.local.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategory(categoryEntity: CategoryEntity)

    @Update
    fun updateCategory(categoryEntity: CategoryEntity)
}