package com.advancedfinance.category.presentation.screen.category_list

import android.os.Bundle
import com.advancedfinance.category.databinding.CategoryFragmentExpenseListBinding
import com.advancedfinance.core.platform.BaseFragment

class ExpenseListFragment : BaseFragment<CategoryFragmentExpenseListBinding, CategoryListViewModel>(
    CategoryFragmentExpenseListBinding::inflate,
    CategoryListViewModel::class
) {
    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {}
}