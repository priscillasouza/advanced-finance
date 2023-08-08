package com.advancedfinance.category.presentation.screen.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.advancedfinance.category.R
import com.advancedfinance.category.databinding.CategoryFragmentCategoryBinding
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch

class CategoryFragment : BaseFragment<CategoryFragmentCategoryBinding, CategoryViewModel>(
    CategoryFragmentCategoryBinding::inflate,
    CategoryViewModel::class
) {
    override fun prepareView(savedInstanceState: Bundle?) {
        onObservable()
        setListeners()
    }

    private fun onObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is CategoryViewState.ViewUpdate -> {
                        preparedViewUpdate(it.categoryModel)
                    }
                    is CategoryViewState.ViewInsert -> {
                        preparedViewInsert()
                    }
                    is CategoryViewState.SuccessUpdate -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.category_text_toast_category_successfully_updated),
                            Toast.LENGTH_SHORT).show()
                    }
                    is CategoryViewState.SuccessInsert -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.category_text_toast_category_successfully_saved),
                            Toast.LENGTH_SHORT).show()
                    }
                    is CategoryViewState.Error -> {
                        it.message
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setListeners() {
        viewBinding.apply {
            buttonSaveCategory.setOnClickListener {
                val name = editTextNameCategory.text.toString()
                val type = if (viewBinding.radioButtonRevenue.isChecked) {
                    TransactionType(
                        id = 1,
                        name = getString(R.string.category_text_radio_group_revenue))
                } else {
                    TransactionType(
                        id = 2,
                        name = getString(R.string.category_text_radio_group_expense))
                }

                viewModel.dispatchViewAction(
                    CategoryViewAction.SaveCategory(
                        typeTransaction = type,
                        name = name
                    ))

                Toast.makeText(context,
                    getString(R.string.category_text_toast_category_successfully_saved),
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.category_action_category_categoryfragment_to_category_categorylistfragment)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun preparedViewUpdate(category: CategoryModel) {
        viewBinding.apply {
            editTextNameCategory.setText(category.name)
            buttonSaveCategory.text = getString(R.string.category_text_button_update_category)
            buttonSaveCategory.setTextColor(com.advancedfinance.core.R.color.core_md_theme_light_onPrimary)
        }
    }

    private fun preparedViewInsert() {
        viewBinding.apply {
            buttonSaveCategory.text = getString(R.string.category_text_button_save_category)
        }
    }
}