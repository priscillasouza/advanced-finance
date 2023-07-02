package com.advancedfinance.account_finance.presentation.screen.account_list

import androidx.lifecycle.viewModelScope
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal

class AccountListViewModel(
    private val repository: IAccountRepository,
) : BaseViewModel<AccountListViewState, AccountListViewAction>() {

    private val listViewStateMutable =
        MutableStateFlow<AccountListViewState>(AccountListViewState.Loading)
    override val listViewState: StateFlow<AccountListViewState> = listViewStateMutable

    override fun dispatchViewAction(viewAction: AccountListViewAction) {
        when (viewAction) {
            is AccountListViewAction.GetListAccount -> getListAccount()
        }
    }

    private fun getListAccount() {
        viewModelScope.launch {
            listViewStateMutable.value = AccountListViewState.Loading
            repository.getAccounts()
                .catch { exception ->
                    exception.printStackTrace()
                    listViewStateMutable.value =
                        AccountListViewState.Error(R.string.account_finance_text_account_list_error.toString())
                }.collect {
                    if (it.isEmpty()) {
                        listViewStateMutable.value = AccountListViewState.Empty
                    }
                    listViewStateMutable.value = AccountListViewState.Success(it, it.sumOf {
                        it.startedBalance
                    })
                }
        }
    }
}

sealed class AccountListViewAction {
    object GetListAccount : AccountListViewAction()
}

sealed class AccountListViewState {
    class Success(val listAccount: List<AccountModel>, val total: BigDecimal) :
        AccountListViewState()

    class Error(val message: String) : AccountListViewState()
    object Loading : AccountListViewState()
    object Empty : AccountListViewState()
}