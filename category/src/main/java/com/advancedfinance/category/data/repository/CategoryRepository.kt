package com.advancedfinance.category.data.repository

import com.advancedfinance.category.data.mapper.MapEntityToModel
import com.advancedfinance.category.data.mapper.MapModelToEntity
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.framework.infrastruture.local.database.category.CategoryDAO

class CategoryRepository(
    private val categoryDAO: CategoryDAO,
    private var mapModelToEntity: MapModelToEntity,
    private var mapEntityToModel: MapEntityToModel
) : ICategoryRepository {

    override suspend fun saveCategory(categoryModel: CategoryModel) {
        mapModelToEntity.transform(categoryModel).also {
            categoryDAO.addCategory(it)
        }
    }

    override suspend fun updateCategory(categoryModel: CategoryModel) {
        mapModelToEntity.transform(categoryModel).also {
            categoryDAO.updateCategory(it)
        }
    }
}