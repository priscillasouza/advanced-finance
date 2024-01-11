package com.advancedfinance.transaction.presentation.screen.transaction_list

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.advancedfinance.account_finance.presentation.screen.account_list.AccountListViewAction
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.category.presentation.screen.category_list.CategoryListFragmentDirections
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.transaction.databinding.TransactionFragmentTransactionListBinding
import com.advancedfinance.transaction.presentation.adapter.TransactionListAdapter
import com.advancedfinance.transaction.presentation.model.TransactionModel
import com.advancedfinance.transaction.presentation.screen.ArgTransactionType
import kotlinx.coroutines.launch

class TransactionListFragment :
    BaseFragment<TransactionFragmentTransactionListBinding, TransactionListViewModel>(
        TransactionFragmentTransactionListBinding::inflate,
        TransactionListViewModel::class
    ) {

    private lateinit var transactionListAdapter: TransactionListAdapter

    /*private var isAllFabVisible: Boolean? = null*/

    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.dispatchViewAction(TransactionListViewAction.GetListTransaction)
        setListeners()
        settingObservable()
        setAdapterTransactionList()
    }

    private fun setListeners() {
        /* viewBinding.apply {
             floatingActionButton.visibility = View.GONE
             floatingActionButtonExpenses.visibility = View.GONE
             textViewFloatingActionButtonRevenues.visibility = View.GONE
             textViewFloatingActionButtonExpenses.visibility = View.GONE
             isAllFabVisible = false

             floatingActionButton.setOnClickListener {
                 (if (!isAllFabVisible!!) {
                     floatingActionButtonRevenues.show()
                     floatingActionButtonExpenses.show()
                     textViewFloatingActionButtonRevenues.visibility = View.VISIBLE
                     textViewFloatingActionButtonExpenses.visibility = View.VISIBLE
                     true
                 } else {
                     floatingActionButtonRevenues.hide()
                     floatingActionButtonExpenses.hide()
                     textViewFloatingActionButtonRevenues.visibility = View.GONE
                     textViewFloatingActionButtonExpenses.visibility = View.GONE
                     false
                 }).also { isAllFabVisible = it }
             }

             floatingActionButtonRevenues.setOnClickListener {
                 findNavController().navigate(TransactionListFragmentDirections.transactionActionTransactionTransactionlistfragmentToTransactionTransactionfragment(argTransactionType = com.advancedfinance.transaction.presentation.screen.transaction.ArgTransactionType.Revenue))
             }

             floatingActionButtonExpenses.setOnClickListener {
                 findNavController().navigate(TransactionListFragmentDirections.transactionActionTransactionTransactionlistfragmentToTransactionTransactionfragment(
                     argTransactionType = com.advancedfinance.transaction.presentation.screen.transaction.ArgTransactionType.Expense))
             }
         }*/
    }

    private fun settingObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is TransactionListViewState.Loading -> showLoading()
                    is TransactionListViewState.Error -> showError(it.message)
                    is TransactionListViewState.SuccessTransactionList -> listAdapterTransaction(it.transactionList)
                    else -> {}
                }
            }
        }
    }

    private fun setAdapterTransactionList() {
        viewBinding.recyclerViewTransactionList.apply {
            transactionListAdapter = TransactionListAdapter() { transaction ->
                    val action = ArgTransactionType.fromInt(transaction.transactionType.id)?.let {
                        TransactionListFragmentDirections.transactionlistfragmentToTransactionfragment(
                            argTransactionModel = transaction)
                    }
                    action?.let {
                        findNavController().navigate(it)
                    }
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = transactionListAdapter
        }
    }

    private fun listAdapterTransaction(list: List<TransactionModel>) {
        transactionListAdapter.setList(list)
    }

    private fun showError(message: Int) {
        Toast.makeText(requireContext(), message,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {}
}

