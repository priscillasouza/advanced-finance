package com.advancedfinance.framework.infrastruture.local.database

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverter {

    @TypeConverter
    @JvmStatic
    fun bigDecimalToDouble(value: BigDecimal): Double? {
        return try {
            value.toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @TypeConverter
    @JvmStatic
    fun doubleToBigDecimal(value: Double): BigDecimal? {
        return try {
            BigDecimal(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}