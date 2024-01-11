package com.advancedfinance.overview.presentation.screen

import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

class OverviewViewModel: BaseViewModel<OverviewViewState, OverviewViewAction>() {

    override val listViewState: StateFlow<OverviewViewState>
        get() {
            TODO()
        }

    override fun dispatchViewAction(viewAction: OverviewViewAction) {
        TODO("Not yet implemented")
    }
}

sealed class OverviewViewAction {}

sealed class OverviewViewState {
    object Success : OverviewViewState()
    object Loading : OverviewViewState()
    class Error(val message: Int) : OverviewViewState()
}