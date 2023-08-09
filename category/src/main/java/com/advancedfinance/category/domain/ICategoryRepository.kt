package com.advancedfinance.category.domain

import com.advancedfinance.category.presentation.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {

    suspend fun saveCategory(categoryModel: CategoryModel)

    suspend fun updateCategory(categoryModel: CategoryModel)

    fun getCategoryType(categoryType: Int): Flow<List<CategoryModel>>
}