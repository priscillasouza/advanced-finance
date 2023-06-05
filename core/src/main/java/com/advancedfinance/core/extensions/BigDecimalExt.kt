package com.advancedfinance.core.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formatCurrencyToBr(): String {
    val formatPrice: NumberFormat = NumberFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return formatPrice.format(this)
}