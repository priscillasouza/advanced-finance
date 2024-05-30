package com.advancedfinance.transaction.presentation.screen.transaction_list

import androidx.lifecycle.viewModelScope
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.screen.category.CategoryViewAction
import com.advancedfinance.core.platform.BaseViewModel
import com.advancedfinance.transaction.R
import com.advancedfinance.transaction.domain.repository.ITransactionRepository
import com.advancedfinance.transaction.presentation.model.TransactionModel
import com.advancedfinance.transaction.presentation.screen.transaction.TransactionViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TransactionListViewModel(
    private val repository: ITransactionRepository
) : BaseViewModel<TransactionListViewState, TransactionListViewAction>() {

    private val listViewStateMutable = MutableStateFlow<TransactionListViewState>(TransactionListViewState.Loading)
    override val listViewState: StateFlow<TransactionListViewState> = listViewStateMutable

    override fun dispatchViewAction(viewAction: TransactionListViewAction) {
        when (viewAction) {
            is TransactionListViewAction.GetListTransaction -> getTransactionList()
        }
    }

    private fun getTransactionList() {
        viewModelScope.launch {
            listViewStateMutable.value = TransactionListViewState.Loading
            repository.getAllTransaction()
                .catch { exception ->
                    exception.printStackTrace()
                    listViewStateMutable.value = TransactionListViewState.Error(R.string.transaction_text_error_transaction_list)
                }.collect {
                    if (it.isEmpty()) {
                        listViewStateMutable.value = TransactionListViewState.Empty
                    }
                    listViewStateMutable.value = TransactionListViewState.SuccessTransactionList(it)
                }
        }
    }
}

sealed class TransactionListViewAction {
    data object GetListTransaction :TransactionListViewAction()
}

sealed class TransactionListViewState {
    data object Loading : TransactionListViewState()
    class SuccessTransactionList(val transactionList: List<TransactionModel>
    ) : TransactionListViewState()
    class Error(val message: Int) : TransactionListViewState()
    data object Empty : TransactionListViewState()
}