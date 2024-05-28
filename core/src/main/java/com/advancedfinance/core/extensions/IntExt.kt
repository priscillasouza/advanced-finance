package com.advancedfinance.core.extensions

fun Int?.orZero() : Int {
    return this ?: 0
}

fun Int?.orNegative() : Int {
    return this ?: -1
}