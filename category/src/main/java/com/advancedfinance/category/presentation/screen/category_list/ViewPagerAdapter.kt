package com.advancedfinance.category.presentation.screen.category_list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragment: FragmentActivity,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryListDetailFragment(categoryType = 1)
            else -> CategoryListDetailFragment(categoryType = 2)
        }
    }
}