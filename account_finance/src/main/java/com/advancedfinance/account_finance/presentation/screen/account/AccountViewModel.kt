package com.advancedfinance.account_finance.presentation.screen.account

import androidx.lifecycle.viewModelScope
import com.advancedfinance.account_finance.R
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
            is AccountViewAction.DeleteAccount -> deleteAccount(viewAction.accountModel)
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
                viewStateMutable.value = AccountViewState.SuccessUpdate
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error(R.string.account_finance_text_account_error)
            }
        }
    }

    private fun addAccount(accountModel: AccountModel) {
        viewModelScope.launch {
            try {
                repository.saveAccount(accountModel)
                viewStateMutable.value = AccountViewState.SuccessInsert
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error(R.string.account_finance_text_account_error)
            }
        }
    }

    private fun deleteAccount(accountModel: AccountModel) {
        viewModelScope.launch {
            try {
                repository.deleteAccount(accountModel)
                viewStateMutable.value = AccountViewState.SuccessDelete
            } catch (e: Exception) {
                viewStateMutable.value = AccountViewState.Error(R.string.account_finance_text_account_error)
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

    class DeleteAccount(val accountModel: AccountModel) : AccountViewAction()
}

sealed class AccountViewState {
    object SuccessInsert : AccountViewState()
    object SuccessUpdate : AccountViewState()
    object SuccessDelete : AccountViewState()
    object Loading : AccountViewState()
    class Error(val message: Int) : AccountViewState()
    class ViewUpdate(val accountModel: AccountModel) : AccountViewState()
    object ViewInsert : AccountViewState()
}