package com.advancedfinance.category.data.mapper

import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity

class MapModelToEntity : IMapper<CategoryModel, CategoryEntity> {

    override fun transform(entity: CategoryModel): CategoryEntity {
        return CategoryEntity(
            id = entity.id,
            name = entity.name,
            fkTransactionTypeId = entity.transactionType.id
        )
    }
}