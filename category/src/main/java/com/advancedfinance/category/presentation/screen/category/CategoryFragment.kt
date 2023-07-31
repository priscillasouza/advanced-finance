package com.advancedfinance.category.presentation.screen.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
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

                var type: TransactionType = TransactionType(id = 1, name = "Receita")
                rgTransactionType.setOnCheckedChangeListener { radioGroup, checkedId ->
                    type = when (checkedId) {
                        R.id.radio_button_revenue -> {
                            TransactionType(id = 1,
                                name = getString(R.string.category_text_radio_group_revenue))
                        }
                        R.id.radio_button_expense -> {
                            TransactionType(id = 2,
                                name = getString(R.string.category_text_radio_group_expense))
                        }
                        else -> {
                            TransactionType(id = 1,
                                name = getString(R.string.category_text_radio_group_revenue))
                        }
                    }
                }

                viewModel.dispatchViewAction(CategoryViewAction.SaveCategory(
                    categoryName = name,
                    typeTransaction = type
                ))
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