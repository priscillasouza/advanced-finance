package com.advancedfinance.category.data.mapper

import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.data.IMapper
import com.advancedfinance.core.extensions.orNegative
import com.advancedfinance.framework.infrastruture.local.database.category.entity.CategoryWithTransactionType

class MapEntityToModel : IMapper<CategoryWithTransactionType, CategoryModel> {

    override fun transform(entity: CategoryWithTransactionType): CategoryModel {
        return CategoryModel(
            id = entity.category?.id.orNegative(),
            name = entity.category?.name.orEmpty(),
            transactionType = entity.transactionType.run {
                com.advancedfinance.category.presentation.model.TransactionType(
                    id = entity.category?.fkTransactionTypeId.orNegative(),
                    name = entity.category?.name.orEmpty()
                )
            }
        )
    }
}