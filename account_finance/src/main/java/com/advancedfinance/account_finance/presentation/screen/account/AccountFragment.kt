package com.advancedfinance.account_finance.presentation.screen.account

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.databinding.AccountFinanceFragmentAccountBinding
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.platform.BaseFragment

class AccountFragment : BaseFragment<AccountFinanceFragmentAccountBinding, AccountViewModel>(
    AccountFinanceFragmentAccountBinding::inflate,
    AccountViewModel::class
) {

    override fun prepareView(savedInstanceState: Bundle?) {
        setAdapterAccountCategories()
        setNavigationIconsToolBar()
    }

    private fun setNavigationIconsToolBar() {
        viewBinding.apply {
            toolbarNewAccount.apply {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_item_save -> {
                            saveNewAccount()
                            Toast.makeText(requireContext(),
                                getString(R.string.account_finance_text_toast_add_success),
                                Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.account_finance_action_account_finance_accountfragment_to_account_finance_accountlistfragment)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
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

    private fun saveNewAccount() {
        val startedBalance = viewBinding.editTextInputNewAccountValue.text.toString().toBigDecimal()
        val name = viewBinding.editTextInputNewAccountName.text.toString()
        val category = viewBinding.autocompleteCategory.text.toString()

        val newAccount = AccountModel(
            0,
            name,
            startedBalance,
            category
        )
        viewModel.dispatchViewAction(AccountViewAction.AddAccount(accountModel = newAccount))
    }
}