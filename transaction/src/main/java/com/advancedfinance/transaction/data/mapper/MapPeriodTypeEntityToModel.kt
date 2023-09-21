package com.advancedfinance.transaction.data.mapper

import com.advancedfinance.core.data.IMapper
import com.advancedfinance.framework.infrastruture.local.database.periodtype.entity.PeriodTypeEntity
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel

class MapPeriodTypeEntityToModel : IMapper<PeriodTypeEntity, PeriodTypeModel> {

    override fun transform(periodType: PeriodTypeEntity): PeriodTypeModel {
        return PeriodTypeModel(
            id = periodType.id,
            name = periodType.name
        )
    }
}