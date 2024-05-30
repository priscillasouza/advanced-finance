package com.advancedfinance.framework.infrastruture.local.database.category

import androidx.room.*
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryWithTransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCategory(categoryEntity: CategoryEntity)

    @Update
    fun updateCategory(categoryEntity: CategoryEntity)

    @Delete
    fun deleteCategory(categoryEntity: CategoryEntity)

    @Transaction
    @Query("SELECT * FROM category WHERE fk_transaction_type_id = :transactionTypeId")
    fun getCategoryType(transactionTypeId: Int): Flow<List<CategoryWithTransactionType>>
}