package com.advancedfinance.account_finance.presentation.screen.account

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
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch

class AccountFragment : BaseFragment<AccountFinanceFragmentAccountBinding, AccountViewModel>(
    AccountFinanceFragmentAccountBinding::inflate,
    AccountViewModel::class
) {

    private val args: AccountFragmentArgs by navArgs()

    override fun prepareView(savedInstanceState: Bundle?) {
        onObservable()
        setNavigationIconsToolBar()
        setArgumentAccount()
        setAdapterAccountCategories()
        setListeners()
    }

    private fun onObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is AccountViewState.ViewUpdate -> {
                        preparedViewUpdate(it.account)
                    }
                    is AccountViewState.ViewInsert -> {
                        preparedViewInsert()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setNavigationIconsToolBar() {
        viewBinding.apply {
            toolbarNewAccount.apply {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
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

        accountCategory.setAdapter(categoryAdapter)
        accountCategory.setOnItemClickListener { adapterView, view, i, l ->
            adapterView.getItemAtPosition(i).toString()
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            buttonSaveAccount.setOnClickListener {
                if (validateFields()) {
                    val startedBalance = editTextInputNewAccountValue.text.toString().toBigDecimal()
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
        }
    }

    private fun preparedViewUpdate(account: AccountModel) {
        viewBinding.apply {
            editTextInputNewAccountValue.setText(account.startedBalance.toString())
            editTextInputNewAccountName.setText(account.name)
            autocompleteCategory.setText(account.accountCategory)
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_update)
            toolbarNewAccount.setTitle(R.string.account_finance_text_toolbar_account_edit)
        }
    }

    private fun preparedViewInsert() {
        viewBinding.apply {
            buttonSaveAccount.text = getString(R.string.account_finance_text_button_account_save)
        }
    }

    private fun validateFields(): Boolean {
        return (viewBinding.editTextInputNewAccountValue.text.toString().isNotEmpty()
                && viewBinding.editTextInputNewAccountName.text.toString().isNotEmpty()
                && viewBinding.autocompleteCategory.text.isNotEmpty())
    }
}