package com.advancedfinance.transaction.presentation.screen

import androidx.lifecycle.viewModelScope
import com.advancedfinance.account_finance.domain.repository.IAccountRepository
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.platform.BaseViewModel
import com.advancedfinance.transaction.R
import com.advancedfinance.transaction.domain.repository.ITransactionRepository
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import com.advancedfinance.transaction.presentation.model.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal

class TransactionViewModel(
    private val repository: ITransactionRepository,
    private val categoryRepository: ICategoryRepository,
    private val accountRepository: IAccountRepository,
) : BaseViewModel<TransactionViewState, TransactionViewAction>() {

    private var transaction: TransactionModel? = null

    private val viewStateMutable =
        MutableStateFlow<TransactionViewState>(TransactionViewState.Loading)
    override val listViewState: StateFlow<TransactionViewState> = viewStateMutable

    override fun dispatchViewAction(viewAction: TransactionViewAction) {
        when (viewAction) {
            is TransactionViewAction.PreparedViewTransaction -> preparedView(viewAction.transactionModel,
                viewAction.type)
            is TransactionViewAction.SaveTransaction -> addOrUpdateTransaction(
                viewAction.id,
                viewAction.value,
                viewAction.description,
                viewAction.date,
                viewAction.category,
                viewAction.account,
                viewAction.observation,
                viewAction.isReceived,
                viewAction.isInstallments,
                viewAction.isFixedValue,
                viewAction.isPayInInstallments,
                viewAction.repetitions,
                viewAction.period,
                viewAction.transactionTypeId
            )
            is TransactionViewAction.GetCategoryList -> getCategoryList(viewAction.categoryType)
            is TransactionViewAction.GetAccountList -> getAccountList()
            is TransactionViewAction.GetPeriodTypeList -> getPeriodTypeList()
        }
    }

    private fun preparedView(transactionModel: TransactionModel?, type: ArgTransactionType) {
        if (transactionModel != null && transactionModel.id!! > 0) {
            this.transaction = transactionModel
            viewStateMutable.value =
                TransactionViewState.ViewUpdate(transactionModel = transactionModel,
                    isRevenue = true)
        } else {
            viewStateMutable.value =
                TransactionViewState.ViewInsert(isRevenue = type == ArgTransactionType.Revenue)
        }
    }

    private fun addOrUpdateTransaction(
        id: Int?,
        value: BigDecimal,
        description: String,
        date: String,
        category: CategoryModel?,
        account: AccountModel?,
        observation: String,
        isReceived: Boolean,
        isInstallments: Boolean,
        isFixedValue: Boolean,
        isPayInInstallments: Boolean,
        repetitions: String,
        period: PeriodTypeModel?,
        transactionTypeId: Int,
    ) {
        transaction?.id?.let { id ->
            if (id > 0) {
                updateTransaction(TransactionModel(
                    id = id,
                    value = value,
                    description = description,
                    date = date,
                    category = category,
                    account = account,
                    observation = observation,
                    isReceived = isReceived,
                    isInstallments = isInstallments,
                    isFixedValue = isFixedValue,
                    isPayInInstallments = isPayInInstallments,
                    repetitions = repetitions,
                    period = period,
                    transactionType = createTransactionType(transactionTypeId)
                ))
            }
        } ?: addTransaction(TransactionModel(
            id = id,
            value = value,
            description = description,
            date = date,
            category = category,
            account = account,
            observation = observation,
            isReceived = isReceived,
            isInstallments = isInstallments,
            isFixedValue = isFixedValue,
            isPayInInstallments = isPayInInstallments,
            repetitions = repetitions,
            period = period,
            transactionType = createTransactionType(transactionTypeId)
        ))
    }

    private fun addTransaction(transactionModel: TransactionModel) {
        viewModelScope.launch {
            try {
                repository.saveTransaction(transactionModel)
                viewStateMutable.value = TransactionViewState.SuccessInsert
            } catch (e: Exception) {
                e.printStackTrace()
                viewStateMutable.value =
                    TransactionViewState.Error(R.string.transaction_text_error_transaction_save)
            }
        }
    }

    private fun updateTransaction(transactionModel: TransactionModel) {
        viewModelScope.launch {
            try {
                repository.updateTransaction(transactionModel)
                viewStateMutable.value = TransactionViewState.SuccessInsert
            } catch (e: Exception) {
                viewStateMutable.value =
                    TransactionViewState.Error(R.string.transaction_text_error_transaction_save)
            }
        }
    }

    private fun createTransactionType(id: Int): TransactionType {
        return try {
            repository.getTransactionTypeById(id)
        } catch (e: Exception) {
            viewStateMutable.value =
                TransactionViewState.Error(R.string.transaction_text_error_transaction_save)
            TransactionType(-1, "")
        }
    }

    private fun getCategoryList(typeCategory: Int) {
        viewModelScope.launch {
            viewStateMutable.value = TransactionViewState.Loading
            categoryRepository.getCategoryType(typeCategory)
                .catch { exception ->
                    exception.printStackTrace()
                    viewStateMutable.value =
                        TransactionViewState.Error(R.string.transaction_text_error_transaction_get_category_list)
                }.collect {
                    if (it.isEmpty()) {
                        viewStateMutable.value = TransactionViewState.Empty
                    }
                    viewStateMutable.value = TransactionViewState.SuccessCategoryList(it)
                }
        }
    }

    private fun getAccountList() {
        viewModelScope.launch {
            viewStateMutable.value = TransactionViewState.Loading
            accountRepository.getAccounts()
                .catch { exception ->
                    exception.printStackTrace()
                    viewStateMutable.value =
                        TransactionViewState.Error(R.string.transaction_text_error_transaction_get_account_list)
                }.collect {
                    viewStateMutable.value = TransactionViewState.SuccessAccountList(it)
                }
        }
    }

    private fun getPeriodTypeList() {
        viewModelScope.launch {
            viewStateMutable.value = TransactionViewState.Loading
            repository.getAllPeriodType()
                .catch { exception ->
                    exception.printStackTrace()
                    viewStateMutable.value =
                        TransactionViewState.Error(R.string.transaction_text_error_transaction_get_period_type_list)
                }.collect {
                    viewStateMutable.value = TransactionViewState.SuccessPeriodTypeList(it)
                }
        }
    }
}

sealed class TransactionViewAction {
    class PreparedViewTransaction(
        val transactionModel: TransactionModel?,
        val type: ArgTransactionType,
    ) : TransactionViewAction()

    class SaveTransaction(
        val id: Int? = null,
        val value: BigDecimal,
        val description: String,
        val date: String,
        val category: CategoryModel?,
        val account: AccountModel?,
        val observation: String,
        val isReceived: Boolean,
        val isInstallments: Boolean,
        val isFixedValue: Boolean,
        val isPayInInstallments: Boolean,
        val repetitions: String,
        val period: PeriodTypeModel?,
        val transactionTypeId: Int
    ) : TransactionViewAction()

    class GetCategoryList(val categoryType: Int) : TransactionViewAction()
    object GetAccountList : TransactionViewAction()
    object GetPeriodTypeList : TransactionViewAction()
}

sealed class TransactionViewState {
    object Loading : TransactionViewState()
    object SuccessInsert : TransactionViewState()
    object Empty : TransactionViewState()
    class SuccessCategoryList(val categoryList: List<CategoryModel>) : TransactionViewState()
    class SuccessAccountList(val accountList: List<AccountModel>) : TransactionViewState()
    class SuccessPeriodTypeList(val periodTypeList: List<PeriodTypeModel>) : TransactionViewState()
    class Error(val message: Int) : TransactionViewState()
    class ViewInsert(val isRevenue: Boolean) : TransactionViewState()
    class ViewUpdate(val transactionModel: TransactionModel, val isRevenue: Boolean) :
        TransactionViewState()
}