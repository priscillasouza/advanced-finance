package com.advancedfinance.category.data.mapper

import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity

class MapModelToEntity : IMapper<CategoryModel, CategoryEntity> {

    override fun transform(category: CategoryModel): CategoryEntity {
        return CategoryEntity(
            id = category.id ?: 0,
            name = category.name,
            fkTransactionTypeId = category.transactionType.id
        )
    }
}