package com.advancedfinance.account_finance.presentation.screen.account

import android.app.AlertDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.databinding.AccountFinanceFragmentAccountBinding
import com.advancedfinance.account_finance.presentation.adapter.AdapterAccountType
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.account_finance.presentation.model.AccountTypeModel
import com.advancedfinance.core.extensions.addCurrencyFormatter
import com.advancedfinance.core.extensions.removeSpecialCharacters
import com.advancedfinance.core.extensions.toMoney
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch

class AccountFragment :
    BaseFragment<AccountFinanceFragmentAccountBinding, AccountViewModel>(
        AccountFinanceFragmentAccountBinding::inflate,
        AccountViewModel::class
    ) {

    private val args: AccountFragmentArgs by navArgs()
    private var accountTypeSelected: AccountTypeModel? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.dispatchViewAction(AccountViewAction.GetListAccountType)
        setListeners()
        setArgumentAccount()
        onObservable()
    }

    private fun onObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is AccountViewState.ViewUpdate -> {
                        preparedViewUpdate(it.accountModel)
                    }
                    is AccountViewState.ViewInsert -> {
                        preparedViewInsert()
                    }
                    is AccountViewState.SuccessUpdate -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.account_finance_text_toast_update_success),
                            Toast.LENGTH_SHORT).show()
                    }
                    is AccountViewState.SuccessInsert -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.account_finance_text_toast_add_success),
                            Toast.LENGTH_SHORT).show()
                    }
                    is AccountViewState.SuccessDelete -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.account_finance_text_toast_delete_success),
                            Toast.LENGTH_SHORT).show()
                    }
                    is AccountViewState.Error -> {
                        it.message
                    }
                    is AccountViewState.SuccessAccountType -> {
                        setAdapterAccountType(
                            viewBinding.autocompleteAccountType,
                            onItemClickListener = { adapter, _, position, _ ->
                                val type = adapter.getItemAtPosition(position) as AccountTypeModel
                                accountTypeSelected = type
                                viewBinding.autocompleteAccountType.setText(accountTypeSelected?.name)
                            },
                            it.listAccountType
                        )
                        viewBinding.autocompleteAccountType.setText(accountTypeSelected?.name)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setArgumentAccount() {
        viewModel.dispatchViewAction(AccountViewAction.PreparedViewAccount(args.account))
    }

    private fun setListeners() {
        viewBinding.apply {

            editTextInputAccountValue.addCurrencyFormatter()

            buttonSaveAccount.setOnClickListener {
                if (validateFields()) {
                    val startedBalance =
                        editTextInputAccountValue.text.toString().removeSpecialCharacters()
                            .toBigDecimal()
                    val name = editTextInputAccountName.text.toString()
                    val type = accountTypeSelected

                    viewModel.dispatchViewAction(AccountViewAction.SaveAccount(
                        startedBalance = startedBalance,
                        name = name,
                        accountType = type
                    ))

                    findNavController().navigate(AccountFragmentDirections.accountFinanceActionAccountFinanceAccountfragmentToAccountFinanceAccountlistfragment())
                } else {
                    Toast.makeText(context,
                        getString(R.string.account_finance_text_toast_validate_fields),
                        Toast.LENGTH_SHORT).show()
                }
            }

            toolbarAccount.apply {
                setNavigationOnClickListener { findNavController().popBackStack() }
                setNavigationIcon(R.drawable.account_finance_ic_arrow_back)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_item_delete -> {
                            setDeleteAccount()
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun preparedViewUpdate(account: AccountModel) {
        viewBinding.apply {
            editTextInputAccountValue.setText(String.format(account.startedBalance.toString()
                .toMoney()))
            editTextInputAccountName.setText(account.name)
            autocompleteAccountType.setText(account.accountType.name)
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_update)
            toolbarAccount.title = getString(R.string.account_finance_text_toolbar_account_update)
        }
    }

    private fun preparedViewInsert() {
        viewBinding.apply {
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_save)
            toolbarAccount.apply {
                setTitle(R.string.account_finance_text_toolbar_account_save)
                menu.removeItem(R.id.menu_item_delete)
            }
        }
    }

    private fun setAdapterAccountType(
        autoCompleteTextView: AutoCompleteTextView,
        onItemClickListener: AdapterView.OnItemClickListener,
        types: List<AccountTypeModel>,
    ) {
        val adapter = AdapterAccountType(
            requireContext(),
            types
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.apply {
            setOnItemClickListener(onItemClickListener)
        }
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setDeleteAccount() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton(getString(R.string.account_finance_text_yes_dialog_delete)) { _, _ ->
                args.account?.let { AccountViewAction.DeleteAccount(it) }
                    ?.let { viewModel.dispatchViewAction(it) }
                findNavController().navigate(AccountFragmentDirections.accountFinanceActionAccountFinanceAccountfragmentToAccountFinanceAccountlistfragment())
            }
            setNegativeButton(getString(R.string.account_finance_text_no_dialog_delete)) { _, _ -> }
            setTitle(String.format(getString(R.string.account_finance_text_dialog_delete),
                args.account?.name))
            setMessage(String.format(getString(R.string.account_finance_text_confirm_dialog_delete),
                args.account?.name))
        }.create().show()
    }

    private fun validateFields(): Boolean {
        return (viewBinding.editTextInputAccountValue.text.toString().isNotEmpty()
                && viewBinding.editTextInputAccountName.text.toString().isNotEmpty()
                && viewBinding.autocompleteAccountType.text.isNotEmpty())
    }
}