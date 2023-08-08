package com.advancedfinance.category.domain

import com.advancedfinance.category.presentation.model.CategoryModel

interface ICategoryRepository {

    suspend fun saveCategory(categoryModel: CategoryModel)

    suspend fun updateCategory(categoryModel: CategoryModel)
}