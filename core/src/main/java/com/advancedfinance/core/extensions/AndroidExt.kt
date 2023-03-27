package com.advancedfinance.core.extensions

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


fun Context.color(@ColorRes colorRes: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(colorRes)
    } else {
        ContextCompat.getColor(this, colorRes)
    }
}

fun Context.getString(@StringRes id: Int): String{
    return this.getString(id)
}
