package com.advancedfinance.core.platform

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T, A> : ViewModel() {

    abstract val listViewState: StateFlow<T>

    abstract fun dispatchViewAction(viewAction: A)
}