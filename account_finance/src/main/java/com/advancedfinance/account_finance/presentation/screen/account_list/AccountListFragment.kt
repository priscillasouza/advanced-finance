package com.advancedfinance.account_finance.presentation.screen.account_list

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.advancedfinance.account_finance.R
import com.advancedfinance.account_finance.databinding.AccountFinanceFragmentAccountListBinding
import com.advancedfinance.core.platform.BaseFragment

class AccountListFragment :
    BaseFragment<AccountFinanceFragmentAccountListBinding, AccountListViewModel>(
        AccountFinanceFragmentAccountListBinding::inflate,
        AccountListViewModel::class
    ) {

    override fun prepareView(savedInstanceState: Bundle?) {
        setClickFloatingActionAddAccount()
    }

    private fun setClickFloatingActionAddAccount() {
        viewBinding.accountFinanceFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.account_finance_action_account_finance_accountlistfragment_to_account_finance_accountfragment)
        }
    }
}