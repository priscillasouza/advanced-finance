package com.advancedfinance.overview.presentation.screen

import android.os.Bundle
import android.view.View
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.overview.databinding.FragmentOverviewBinding

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
            textViewFloatingActionButtonRevenues.visibility = View.GONE
            isAllFabVisible = false

            floatingActionButton.setOnClickListener {
                (if (!isAllFabVisible!!) {
                    floatingActionButtonRevenues.show()
                    textViewFloatingActionButtonRevenues.visibility = View.VISIBLE
                    true
                } else {
                    floatingActionButtonRevenues.hide()
                    textViewFloatingActionButtonRevenues.visibility = View.GONE
                    false
                }).also { isAllFabVisible = it }
            }

            floatingActionButtonRevenues.setOnClickListener {
               /* findNavController().navigate(R.id.)*/
            }
        }
    }
}