package com.advancedfinance.core.extensions

import android.net.Uri
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Throws(NumberFormatException::class)
fun String.toMoney(): String {
    val stringToLong = this.toLong()
    return getNumberFormat().format(stringToLong.toFraction())
}

fun String.removeSpecialCharacters(): String {
    val stringReturn = String(toCharArray())
    return stringReturn.replace("$", "", true)
        .replace(",", "", true)
        .replace(".", "", true)
        .replace("R", "", true)
        .replace("%", "", true)
        .trim()
}

private fun getNumberFormat(): DecimalFormat {
    return (NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat)
        .apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
}

fun Long.toFraction(): Double {
    return this.toDouble() / 100.0f
}

fun String.toUri(): Uri = Uri.parse(this)