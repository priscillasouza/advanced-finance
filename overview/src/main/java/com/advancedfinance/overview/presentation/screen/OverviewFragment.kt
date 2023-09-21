package com.advancedfinance.overview.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.overview.databinding.FragmentOverviewBinding
import com.advancedfinance.transaction.presentation.screen.ArgTransactionType

class OverviewFragment : BaseFragment<FragmentOverviewBinding, OverviewViewModel>(
    FragmentOverviewBinding::inflate,
    OverviewViewModel::class
) {

    private var isAllFabVisible: Boolean? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
        viewBinding.apply {
            floatingActionButtonRevenues.visibility = View.GONE
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
                findNavController().navigate(OverviewFragmentDirections.overviewActionToTransactionNavigation(
                    argTransactionType = ArgTransactionType.Revenue))
            }

            floatingActionButtonExpenses.setOnClickListener {
                findNavController().navigate(OverviewFragmentDirections.overviewActionToTransactionNavigation(
                    argTransactionType = ArgTransactionType.Expense))
            }
        }
    }
}