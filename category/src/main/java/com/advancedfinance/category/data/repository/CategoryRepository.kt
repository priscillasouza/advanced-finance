package com.advancedfinance.category.data.repository

import com.advancedfinance.category.data.mapper.MapEntityToModel
import com.advancedfinance.category.data.mapper.MapModelToEntity
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.framework.infrastruture.local.database.category.CategoryDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override suspend fun deleteCategory(categoryModel: CategoryModel) {
        mapModelToEntity.transform(categoryModel).also {
            categoryDAO.deleteCategory(it)
        }
    }

    override fun getCategoryType(categoryType: Int): Flow<List<CategoryModel>> {
        return categoryDAO.getCategoryType(categoryType).map {
            mapEntityToModel.transform(it)
        }
    }
}