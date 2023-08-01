package com.advancedfinance.transaction.presentation.screen

import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

class TransactionsViewModel : BaseViewModel<TransactionsViewState, TransactionsViewAction>() {

    override val listViewState: StateFlow<TransactionsViewState>
        get() {
            TODO()
        }

    override fun dispatchViewAction(viewAction: TransactionsViewAction) {
        TODO("Not yet implemented")
    }
}

sealed class TransactionsViewAction {}

sealed class TransactionsViewState {
    object Success : TransactionsViewState()
    object Loading : TransactionsViewState()
    class Error(val message: Int) : TransactionsViewState()
}