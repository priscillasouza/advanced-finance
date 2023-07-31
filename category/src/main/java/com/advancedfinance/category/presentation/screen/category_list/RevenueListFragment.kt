package com.advancedfinance.category.presentation.screen.category_list

import android.os.Bundle
import com.advancedfinance.category.databinding.CategoryFragmentRevenueListBinding
import com.advancedfinance.core.platform.BaseFragment

class RevenueListFragment : BaseFragment<CategoryFragmentRevenueListBinding, CategoryListViewModel>(
    CategoryFragmentRevenueListBinding::inflate,
    CategoryListViewModel::class
) {
    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {}
}