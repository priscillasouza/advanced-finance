package com.advancedfinance.category.data.mapper

import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryEntity

class MapEntityToModel : IMapper<CategoryEntity, CategoryModel> {

    override fun transform(entity: CategoryEntity): CategoryModel {
        return CategoryModel(
            id = entity.id,
            name = entity.name,
            transactionType = entity.id.run {
                com.advancedfinance.category.presentation.model.TransactionType(
                    id = entity.id,
                    name = entity.name
                )
            }
        )
    }
}