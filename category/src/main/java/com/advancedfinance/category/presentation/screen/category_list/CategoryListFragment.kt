package com.advancedfinance.category.presentation.screen.category_list

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.advancedfinance.category.R
import com.advancedfinance.category.databinding.CategoryFragmentCategoryListBinding
import com.advancedfinance.core.platform.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoryListFragment :
    BaseFragment<CategoryFragmentCategoryListBinding, CategoryListViewModel>(
        CategoryFragmentCategoryListBinding::inflate,
        CategoryListViewModel::class
    ) {

    override fun prepareView(savedInstanceState: Bundle?) {
        setListeners()
        setViewPager()
    }

    private fun setListeners() {
        viewBinding.apply {
            floatingActionButtonAddCategory.setOnClickListener {
                findNavController().navigate(R.id.category_action_category_categorylistfragment_to_category_categoryfragment)
            }
        }
    }

    private fun setViewPager() {
        val tabLayout: TabLayout = viewBinding.tabLayoutCategoryList
        val viewPager2: ViewPager2 = viewBinding.viewPagerCategoryList
        viewPager2.adapter = ViewPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.category_text_tab_layout_revenues)
                1 -> tab.text = getString(R.string.category_text_tab_layout_expenses)
            }
        }.attach()
    }
}