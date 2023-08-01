package com.advancedfinance.transaction.presentation.screen

import android.os.Bundle
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.transaction.databinding.TransactionFragmentTransactionBinding

class TransactionFragment : BaseFragment<TransactionFragmentTransactionBinding, TransactionsViewModel >(
    TransactionFragmentTransactionBinding::inflate,
    TransactionsViewModel::class
) {
    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
    }
}