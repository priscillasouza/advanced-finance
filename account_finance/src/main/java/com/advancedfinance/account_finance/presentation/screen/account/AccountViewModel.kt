package com.advancedfinance.account_finance.presentation.screen.account

import androidx.lifecycle.viewModelScope
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: IAccountRepository,
) : BaseViewModel<AccountViewState, AccountViewAction>() {

    private val viewStateMutable = MutableStateFlow<AccountViewState>(AccountViewState.Loading)
    override val viewState: StateFlow<AccountViewState> = viewStateMutable

    override fun dispatchViewAction(viewAction: AccountViewAction) {
        when (viewAction) {
            is AccountViewAction.AddAccount -> {
                addAccount(viewAction.accountModel)
            }
            else -> {}
        }
    }

    private fun addAccount(accountModel: AccountModel) {
        viewModelScope.launch {
            try {
                repository.addAccount(accountModel)
                viewStateMutable.value = AccountViewState.Success
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error
            }
        }
    }
}

sealed class AccountViewAction {
    class AddAccount(val accountModel: AccountModel) : AccountViewAction()
}

sealed class AccountViewState {
    object Success : AccountViewState()
    object Loading : AccountViewState()
    object Error : AccountViewState()
}