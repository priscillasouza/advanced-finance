package com.advancedfinance.transaction.presentation.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.extensions.addCurrencyFormatter
import com.advancedfinance.core.extensions.removeSpecialCharacters
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.transaction.R
import com.advancedfinance.transaction.databinding.TransactionFragmentTransactionBinding
import com.advancedfinance.transaction.presentation.adapter.AdapterAccountList
import com.advancedfinance.transaction.presentation.adapter.AdapterCategoryList
import com.advancedfinance.transaction.presentation.adapter.AdapterPeriodTypeList
import com.advancedfinance.transaction.presentation.model.PeriodTypeModel
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.*

class TransactionFragment :
    BaseFragment<TransactionFragmentTransactionBinding, TransactionViewModel>(
        TransactionFragmentTransactionBinding::inflate,
        TransactionViewModel::class
    ) {

    private var categorySelected: CategoryModel? = null
    private var accountSelected: AccountModel? = null
    private var periodTypeSelected: PeriodTypeModel? = null
    private var isInstallments = false
    private var isReceived = true
    private var isFixedValue = false
    private var isPayInInstallments = false
    private val args by navArgs<TransactionFragmentArgs>()
    private val type by lazy { args.argTransactionType }
    private val transactionModel by lazy { args.argTransactionModel }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.dispatchViewAction(TransactionViewAction.GetAccountList)
        viewModel.dispatchViewAction(TransactionViewAction.GetPeriodTypeList)
        viewModel.dispatchViewAction(TransactionViewAction.PreparedViewTransaction(
            transactionModel = transactionModel,
            type = type))
        setListeners()
        onObservable()
        setDataPickerDialog()
    }

    private fun setListeners() {
        viewBinding.apply {
            toolbarTransaction.apply {
                setNavigationOnClickListener { findNavController().popBackStack() }
                setNavigationIcon(R.drawable.transaction_ic_arrow_back)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_item_transaction_save -> {
                            transactionSave()
                            true
                        }
                        else -> false
                    }
                }
            }
            checkboxReceivedOrPay.setOnCheckedChangeListener { _, isChecked ->
                isReceived = isChecked
            }
            checkboxInstallment.setOnCheckedChangeListener { _, isChecked ->
                isInstallments = isChecked
                radioGroup.isVisible = isInstallments
                textInputRepetitions.isVisible = isInstallments
                textInputPeriodOption.isVisible = isInstallments
            }
            radioGroup.setOnCheckedChangeListener { _, _ ->
                if (radioButtonFixedValue.isChecked) {
                    textInputRepetitions.hint =
                        getString(R.string.transaction_text_hint_repetitions)
                } else {
                    textInputRepetitions.hint =
                        getString(R.string.transaction_text_hint_installments)
                }
            }
        }
    }

    private fun onObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is TransactionViewState.ViewInsert -> {
                        preparedViewTransactionInsert(it.isRevenue)
                    }
                    is TransactionViewState.SuccessInsert -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.transaction_text_toast_success_transaction_save),
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    is TransactionViewState.Error -> {
                        it.message
                    }
                    is TransactionViewState.SuccessCategoryList -> {
                        setAdapterCategoryList(
                            viewBinding.autocompleteCategory,
                            onItemClickListener = { adapter, _, position, _ ->
                                val category = adapter.getItemAtPosition(position) as CategoryModel
                                categorySelected = category
                                viewBinding.autocompleteCategory.setText(categorySelected?.name)
                            },
                            it.categoryList
                        )
                        viewBinding.autocompleteCategory.setText(categorySelected?.name)
                    }

                    is TransactionViewState.SuccessAccountList -> {
                        setAdapterAccountList(
                            viewBinding.autocompleteAccount,
                            onItemClickListener = { adapter, _, position, _ ->
                                val account = adapter.getItemAtPosition(position) as AccountModel
                                accountSelected = account
                                viewBinding.autocompleteAccount.setText(accountSelected?.name)
                            },
                            it.accountList
                        )
                        viewBinding.autocompleteAccount.setText(accountSelected?.name)
                    }
                    is TransactionViewState.SuccessPeriodTypeList -> {
                        setAdapterPeriodTypeList(
                            viewBinding.autocompletePeriodOption,
                            onItemClickListener = { adapter, _, position, _ ->
                                val periodType =
                                    adapter.getItemAtPosition(position) as PeriodTypeModel
                                periodTypeSelected = periodType
                                viewBinding.autocompletePeriodOption.setText(periodTypeSelected?.name)
                            },
                            it.periodTypeList
                        )
                        viewBinding.autocompletePeriodOption.setText(periodTypeSelected?.name)

                    }
                    else -> {}
                }
            }
        }
    }

    private fun preparedViewTransactionInsert(isRevenue: Boolean) {
        viewBinding.apply {
            if (isRevenue) {
                viewModel.dispatchViewAction(TransactionViewAction.GetCategoryList(categoryType = 1))
                toolbarTransaction.apply {
                    title = getString(R.string.transaction_text_toolbar_new_revenue)
                    toolbarTransaction.setBackgroundColor(resources.getColor(com.advancedfinance.core.R.color.core_md_theme_light_tertiary))
                }
                val window = activity?.window
                window?.statusBarColor = ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_dark_onSecondary)
                checkboxReceivedOrPay.setText(R.string.transaction_text_check_box_received)
                checkboxReceivedOrPay.buttonTintList = setColorScreenRevenue()
                checkboxInstallment.buttonTintList = setColorScreenRevenue()
                radioButtonFixedValue.buttonTintList = setColorScreenRevenue()
                radioButtonPayInInstallments.buttonTintList = setColorScreenRevenue()
                textInputValue.setStartIconTintList(setColorScreenRevenue())
                textInputDescription.setStartIconTintList(setColorScreenRevenue())
                textInputDate.setStartIconTintList(setColorScreenRevenue())
                textInputCategory.setStartIconTintList(setColorScreenRevenue())
                textInputAccount.setStartIconTintList(setColorScreenRevenue())
                textInputObservation.setStartIconTintList(setColorScreenRevenue())
            } else {
                viewModel.dispatchViewAction(TransactionViewAction.GetCategoryList(categoryType = 2))
                toolbarTransaction.apply {
                    title = getString(R.string.transaction_text_toolbar_new_expense)
                    toolbarTransaction.setBackgroundColor(resources.getColor(com.advancedfinance.core.R.color.core_md_theme_light_error))
                }
                val window = activity?.window
                window?.statusBarColor = ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_dark_errorContainer)
                checkboxReceivedOrPay.setText(R.string.transaction_text_check_box_pay)
                checkboxReceivedOrPay.buttonTintList = setColorScreenExpense()
                checkboxInstallment.buttonTintList = setColorScreenExpense()
                radioButtonFixedValue.buttonTintList = setColorScreenExpense()
                radioButtonPayInInstallments.buttonTintList = setColorScreenExpense()
                textInputValue.setStartIconTintList(setColorScreenExpense())
                textInputDescription.setStartIconTintList(setColorScreenExpense())
                textInputDate.setStartIconTintList(setColorScreenExpense())
                textInputCategory.setStartIconTintList(setColorScreenExpense())
                textInputAccount.setStartIconTintList(setColorScreenExpense())
                textInputObservation.setStartIconTintList(setColorScreenExpense())
            }
        }
    }

    private fun setColorScreenRevenue(): ColorStateList {
        return ColorStateList.valueOf(resources.getColor(com.advancedfinance.core.R.color.core_md_theme_light_tertiary))
    }

    private fun setColorScreenExpense(): ColorStateList {
        return ColorStateList.valueOf(resources.getColor(com.advancedfinance.core.R.color.core_md_theme_light_error))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SetTextI18n")
    private fun setDataPickerDialog() {
        viewBinding.apply {
            editTextDate.setOnClickListener {
                if(type == ArgTransactionType.Revenue) {
                    resources.configuration.setLocale(Locale("pt", "BR"))
                    val getDate = GregorianCalendar.getInstance()
                    val datePicker = DatePickerDialog(
                        requireContext(),
                        com.advancedfinance.core.R.style.CoreStyleDatePickerRevenue,
                        { _, mYear, mMonth, mDay ->
                            val selectDate = GregorianCalendar.getInstance()
                            editTextDate.setText("$mDay-$mMonth-$mYear")
                            selectDate.set(GregorianCalendar.YEAR, mYear)
                            selectDate.set(GregorianCalendar.MONTH, mMonth)
                            selectDate.set(GregorianCalendar.DAY_OF_MONTH, mDay)
                        },
                        getDate.get(GregorianCalendar.YEAR),
                        getDate.get(GregorianCalendar.MONTH),
                        getDate.get(GregorianCalendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                    datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_light_tertiary))
                    datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_light_tertiary))
                } else {
                    resources.configuration.setLocale(Locale("pt", "BR"))
                    val getDate = GregorianCalendar.getInstance()
                    val datePicker = DatePickerDialog(
                        requireContext(),
                        com.advancedfinance.core.R.style.CoreStyleDatePickerExpense,
                        { _, mYear, mMonth, mDay ->
                            val selectDate = GregorianCalendar.getInstance()
                            editTextDate.setText("$mDay-$mMonth-$mYear")
                            selectDate.set(GregorianCalendar.YEAR, mYear)
                            selectDate.set(GregorianCalendar.MONTH, mMonth)
                            selectDate.set(GregorianCalendar.DAY_OF_MONTH, mDay)
                        },
                        getDate.get(GregorianCalendar.YEAR),
                        getDate.get(GregorianCalendar.MONTH),
                        getDate.get(GregorianCalendar.DAY_OF_MONTH)
                    )
                    datePicker.show()
                    datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_light_error))
                    datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), com.advancedfinance.core.R.color.core_md_theme_light_error))
                }
            }
        }
    }

    private fun setAdapterCategoryList(
        autoCompleteTextView: AutoCompleteTextView,
        onItemClickListener: AdapterView.OnItemClickListener,
        categories: List<CategoryModel>,
    ) {
        val adapter = AdapterCategoryList(
            requireContext(),
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.apply {
            setOnItemClickListener(onItemClickListener)
        }
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setAdapterAccountList(
        autoCompleteTextView: AutoCompleteTextView,
        onItemClickListener: AdapterView.OnItemClickListener,
        accounts: List<AccountModel>,
    ) {
        val adapter = AdapterAccountList(
            requireContext(),
            accounts
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.apply {
            setOnItemClickListener(onItemClickListener)
        }
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setAdapterPeriodTypeList(
        autoCompleteTextView: AutoCompleteTextView,
        onItemClickListener: AdapterView.OnItemClickListener,
        periodTypes: List<PeriodTypeModel>,
    ) {
        val adapter = AdapterPeriodTypeList(
            requireContext(),
            periodTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.apply {
            setOnItemClickListener(onItemClickListener)
        }
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun validateFields(): Boolean {
        return (viewBinding.editTextInputValue.text.toString().isNotEmpty()
                && viewBinding.editTextDescription.text.toString().isNotEmpty()
                && viewBinding.editTextDate.text.toString().isNotEmpty()
                && viewBinding.autocompleteCategory.text.isNotEmpty()
                && viewBinding.autocompleteAccount.text.isNotEmpty())
    }

    private fun transactionSave() {
        viewBinding.apply {
            editTextInputValue.addCurrencyFormatter()
            if (validateFields()) {
                val value =
                    editTextInputValue.text.toString().removeSpecialCharacters().toBigDecimal()
                val description = editTextDescription.text.toString()
                val date = editTextDate.text.toString()
                val category = categorySelected
                val account = accountSelected
                val observation = editTextInputObservation.text.toString()
                val repetitions = editTextInputRepetitions.text.toString()
                val period = periodTypeSelected
                val transactionTypeId = transactionModel?.transactionType?.id ?: type.value

                viewModel.dispatchViewAction(TransactionViewAction.SaveTransaction(
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
                    transactionTypeId = transactionTypeId
                ))
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.transaction_text_toast_validate_fields),
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

enum class ArgTransactionType(val value: Int) : Serializable {
    Revenue(1),
    Expense(2)
}