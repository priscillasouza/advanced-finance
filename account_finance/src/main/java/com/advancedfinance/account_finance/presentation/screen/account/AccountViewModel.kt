package com.advancedfinance.account_finance.presentation.screen.account

import androidx.lifecycle.viewModelScope
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class AccountViewModel(
    private val repository: IAccountRepository,
) : BaseViewModel<AccountViewState, AccountViewAction>() {

    private var account: AccountModel? = null

    private val viewStateMutable = MutableStateFlow<AccountViewState>(AccountViewState.Loading)
    override val listViewState: StateFlow<AccountViewState> = viewStateMutable

    override fun dispatchViewAction(viewAction: AccountViewAction) {
        when (viewAction) {
            is AccountViewAction.PreparedViewAccount -> {
                preparedView(viewAction.accountModel)
            }
            is AccountViewAction.SaveAccount -> {
                addOrUpdateAccount(viewAction.name, viewAction.startedBalance, viewAction.category)
            }
        }
    }

    private fun preparedView(accountModel: AccountModel?) {
        if (accountModel != null && accountModel.id!! > 0) {
            this.account = accountModel
            viewStateMutable.value = AccountViewState.ViewUpdate(accountModel)
        } else {
            viewStateMutable.value = AccountViewState.ViewInsert
        }
    }

    private fun addOrUpdateAccount(
        name: String,
        startedBalance: BigDecimal,
        category: String,
    ) {
        account?.id?.let { id ->
            if (id > 0) {
                updateAccount(AccountModel(id = id,
                    name = name,
                    accountCategory = category,
                    startedBalance = startedBalance))
            }
        } ?: addAccount(AccountModel(id = null,
            name = name,
            accountCategory = category,
            startedBalance = startedBalance))
    }

    private fun updateAccount(accountModel: AccountModel) {
        viewModelScope.launch {
            try {
                repository.updateAccount(accountModel)
                viewStateMutable.value = AccountViewState.Success
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error
            }
        }
    }

    private fun addAccount(accountModel: AccountModel) {
        viewModelScope.launch {
            try {
                repository.saveAccount(accountModel)
                viewStateMutable.value = AccountViewState.Success
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error
            }
        }
    }
}

sealed class AccountViewAction {
    class PreparedViewAccount(val accountModel: AccountModel?) : AccountViewAction()
    class SaveAccount(
        val name: String,
        val startedBalance: BigDecimal,
        val category: String,
    ) : AccountViewAction()
}

sealed class AccountViewState {
    object Success : AccountViewState()
    object Loading : AccountViewState()
    object Error : AccountViewState()
    class ViewUpdate(val account: AccountModel) : AccountViewState()
    object ViewInsert : AccountViewState()
}