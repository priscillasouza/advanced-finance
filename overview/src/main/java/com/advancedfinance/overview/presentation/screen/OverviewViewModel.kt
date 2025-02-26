package com.advancedfinance.overview.presentation.screen

import com.advancedfinance.core.platform.BaseViewModel
import com.advancedfinance.overview.domain.usecase.InitOverviewUseCase
import kotlinx.coroutines.flow.StateFlow

class OverviewViewModel(
    private val repository: InitOverviewUseCase
): BaseViewModel<OverviewViewState, OverviewViewAction>() {

    override val listViewState: StateFlow<OverviewViewState>
        get() {
            TODO()
        }

    override fun dispatchViewAction(viewAction: OverviewViewAction) {
        when(viewAction) {
           is OverviewViewAction.Init -> {
               //bater no usecase chamar o que precisa para enviar para a view
           }
        }
    }
}

sealed class OverviewViewAction {
    object Init:OverviewViewAction()
}

sealed class OverviewViewState {
    object Success : OverviewViewState()
    object Loading : OverviewViewState()
    class Error(val message: Int) : OverviewViewState()
}