package com.advancedfinance.core.platform

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val TAG = javaClass.simpleName

    open fun onDestroyView(){}
}