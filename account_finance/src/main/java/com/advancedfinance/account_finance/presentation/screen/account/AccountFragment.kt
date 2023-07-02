package com.advancedfinance.account_finance.presentation.screen.account

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.databinding.AccountFinanceFragmentAccountBinding
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.extensions.addCurrencyFormatter
import com.advancedfinance.core.extensions.removeSpecialCharacters
import com.advancedfinance.core.extensions.toMoney
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch

class AccountFragment : BaseFragment<AccountFinanceFragmentAccountBinding, AccountViewModel>(
    AccountFinanceFragmentAccountBinding::inflate,
    AccountViewModel::class
) {

    private val args: AccountFragmentArgs by navArgs()

    override fun prepareView(savedInstanceState: Bundle?) {
        onObservable()
        setArgumentAccount()
        setAdapterAccountCategories()
        setListeners()
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
                    else -> {}
                }
            }
        }
    }

    private fun setArgumentAccount() {
        viewModel.dispatchViewAction(AccountViewAction.PreparedViewAccount(args.account))
    }

    private fun setAdapterAccountCategories() {
        val accountCategory: AutoCompleteTextView = viewBinding.autocompleteCategory
        val listCategory = ArrayList<String>()

        listCategory.add(getString(R.string.account_finance_text_category_money))
        listCategory.add(getString(R.string.account_finance_text_category_current_account))
        listCategory.add(getString(R.string.account_finance_text_category_savings))
        listCategory.add(getString(R.string.account_finance_text_category_others))

        val categoryAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listCategory)

        accountCategory.apply {
            setAdapter(categoryAdapter)
            accountCategory.setOnItemClickListener { adapterView, view, i, l ->
                adapterView.getItemAtPosition(i).toString()
            }
        }
    }

    private fun setListeners() {
        viewBinding.apply {

            editTextInputNewAccountValue.addCurrencyFormatter()

            buttonSaveAccount.setOnClickListener {
                if (validateFields()) {
                    val startedBalance =
                        editTextInputNewAccountValue.text.toString().removeSpecialCharacters()
                            .toBigDecimal()
                    val name = editTextInputNewAccountName.text.toString()
                    val category = autocompleteCategory.text.toString()

                    viewModel.dispatchViewAction(AccountViewAction.SaveAccount(
                        startedBalance = startedBalance,
                        name = name,
                        category = category))

                    findNavController().navigate(AccountFragmentDirections.accountFinanceActionAccountFinanceAccountfragmentToAccountFinanceAccountlistfragment())
                } else {
                    Toast.makeText(context,
                        getString(R.string.account_finance_text_toast_validate_fields),
                        Toast.LENGTH_SHORT).show()
                }
            }

            toolbarAccount.apply {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
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
            editTextInputNewAccountValue.setText(account.startedBalance.toString().toMoney())
            editTextInputNewAccountName.setText(account.name)
            autocompleteCategory.setText(account.accountCategory)
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_update)
            toolbarAccount.setTitle(R.string.account_finance_text_toolbar_account_edit)
        }
    }

    private fun preparedViewInsert() {
        viewBinding.apply {
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_save)
            toolbarAccount.menu.removeItem(R.id.menu_item_delete)
        }
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
        return (viewBinding.editTextInputNewAccountValue.text.toString().isNotEmpty()
                && viewBinding.editTextInputNewAccountName.text.toString().isNotEmpty()
                && viewBinding.autocompleteCategory.text.isNotEmpty())
    }
}