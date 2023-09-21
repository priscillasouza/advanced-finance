package com.advancedfinance.core.extensions

import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
const val FORMAT_DATE_VIEW = "dd/MM/yyyy"
const val FORMAT_DATE_VIEW_SHORT = "dd MMMM"
const val FORMAT_HOUR = "HH:mm"

fun Calendar.toString(stringFormat: String): String {
    val simpleDateFormat = SimpleDateFormat(stringFormat)
    val dateStringFormated = simpleDateFormat.format(this.time)
    return dateStringFormated
}

fun Date.toString(stringFormat: String): String {
    val simpleDateFormat = SimpleDateFormat(stringFormat)
    val dateStringFormated = simpleDateFormat.format(this.time)
    return dateStringFormated
}

fun Date.differ(secondDate: Date): String {
    val firstDate = this

    val differDate = Date(firstDate.time - secondDate.time)

    return differDate.toString(FORMAT_HOUR)
}

fun String.differTime(secondDateString: String): String {

    if(this.isEmpty() || secondDateString.isEmpty()) return ""
    val firstDate = converterStringToDate(this)
    val secondDate = converterStringToDate(secondDateString)
    return secondDate?.let {
        firstDate?.differ(it)
    }?:""
}

private fun converterStringToDate(stringDate: String): Date? {
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
    return stringDate.takeIf {
        it.isNotEmpty()
    }?.let {
        simpleDateFormat.parse(stringDate)
    }
}