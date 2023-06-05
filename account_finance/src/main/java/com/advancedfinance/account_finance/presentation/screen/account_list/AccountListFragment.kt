package com.advancedfinance.account_finance.presentation.screen.account_list

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.databinding.AccountFinanceFragmentAccountListBinding
import com.advancedfinance.account_finance.presentation.adapter.AccountListAdapter
import com.advancedfinance.account_finance.presentation.model.AccountModel
import com.advancedfinance.core.extensions.formatCurrencyToBr
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch
import java.math.BigDecimal

class AccountListFragment :
    BaseFragment<AccountFinanceFragmentAccountListBinding, AccountListViewModel>(
        AccountFinanceFragmentAccountListBinding::inflate,
        AccountListViewModel::class
    ) {

    private lateinit var accountListAdapter: AccountListAdapter

    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.dispatchViewAction(AccountListViewAction.GetListAccount)
        setClickFloatingActionAddAccount()
        setAdapterListAccount()
        settingObservable()
    }

    private fun settingObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is AccountListViewState.Loading -> showLoading()
                    is AccountListViewState.Error -> showError(it.message)
                    is AccountListViewState.Success -> listAdapterAccount(it.listAccount, it.totalBalance)
                    else -> {}
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {}

    private fun setClickFloatingActionAddAccount() {
        viewBinding.accountFinanceFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.account_finance_action_account_finance_accountlistfragment_to_account_finance_accountfragment)
        }
    }

    private fun setAdapterListAccount() {
        viewBinding.recyclerViewAccountList.apply {
            accountListAdapter = AccountListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = accountListAdapter
        }
    }

    private fun listAdapterAccount(list: List<AccountModel>, totalBalance: BigDecimal) {
        accountListAdapter.setList(list)
        viewBinding.textViewValueTotalBalance.text = totalBalance.formatCurrencyToBr()
    }
}