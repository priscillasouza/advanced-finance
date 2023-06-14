package com.advancedfinance.account_finance.presentation.screen.account_list

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        settingObservable()
        setAdapterListAccount()
        setClickFloatingActionAddAccount()
    }

    private fun settingObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is AccountListViewState.Loading -> showLoading()
                    is AccountListViewState.Error -> showError(it.message)
                    is AccountListViewState.Success -> listAdapterAccount(it.listAccount, it.total)
                    else -> {}
                }
            }
        }
    }

    private fun setAdapterListAccount() {
        viewBinding.recyclerViewAccountList.apply {
            accountListAdapter = AccountListAdapter() { account ->
                val action =
                    AccountListFragmentDirections.accountFinanceActionAccountFinanceAccountlistfragmentToAccountFinanceAccountfragment(
                        account)
                findNavController().navigate(action)
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = accountListAdapter

        }
    }

    private fun listAdapterAccount(list: List<AccountModel>, total: BigDecimal) {
        accountListAdapter.setList(list)
        viewBinding.textViewValueTotalBalance.text = total.formatCurrencyToBr()
    }

    private fun setClickFloatingActionAddAccount() {
        viewBinding.accountFinanceFloatingActionButton.setOnClickListener {
            val action =
                AccountListFragmentDirections.accountFinanceActionAccountFinanceAccountlistfragmentToAccountFinanceAccountfragment()
            findNavController().navigate(action)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {}

}