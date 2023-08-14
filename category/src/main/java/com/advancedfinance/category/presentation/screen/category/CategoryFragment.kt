package com.advancedfinance.category.presentation.screen.category

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val args: CategoryFragmentArgs by navArgs()

    override fun prepareView(savedInstanceState: Bundle?) {
        onObservable()
        setArgumentCategory()
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
                    is CategoryViewState.SuccessDelete -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.category_text_toast_category_successfully_deleted),
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

    private fun setArgumentCategory() {
        viewModel.dispatchViewAction(CategoryViewAction.PreparedViewCategory(args.category))
    }

    private fun setListeners() {
        viewBinding.apply {
            buttonSaveCategory.setOnClickListener {
                if (validateFields()) {
                    val name = editTextNameCategory.text.toString()
                    val type = if (radioButtonRevenue.isChecked) {
                        TransactionType(
                            id = 1,
                            name = getString(R.string.category_text_radio_group_revenue))
                    } else {
                        TransactionType(
                            id = 2,
                            name = getString(R.string.category_text_radio_group_expense))
                    }

                    viewModel.dispatchViewAction(CategoryViewAction.SaveCategory(
                        typeTransaction = type,
                        name = name))
                    findNavController().navigate(R.id.category_action_category_categoryfragment_to_category_categorylistfragment)
                } else {
                    Toast.makeText(context,
                        getString(R.string.category_text_toast_validate_fields),
                        Toast.LENGTH_SHORT).show()
                }
            }

            toolbarCategory.apply {
                setNavigationIcon(R.drawable.category_ic_arrow_back)
                setNavigationOnClickListener { findNavController().popBackStack() }
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_item_delete -> {
                            setDeleteCategory()
                            true
                        }
                        else -> false
                    }
                }

            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun preparedViewUpdate(category: CategoryModel) {
        viewBinding.apply {
            editTextNameCategory.setText(category.name)
            buttonSaveCategory.text = getString(R.string.category_text_button_update_category)
            toolbarCategory.title = getString(R.string.category_text_toolbar_update_category)
        }
    }

    private fun preparedViewInsert() {
        viewBinding.apply {
            buttonSaveCategory.text = getString(R.string.category_text_button_save_category)
            toolbarCategory.title = getString(R.string.category_text_toolbar_save_category)
            toolbarCategory.menu.removeItem(R.id.menu_item_delete)
        }
    }

    private fun setDeleteCategory() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton(getString(R.string.category_text_set_positive_button_dialog)) { _, _ ->
                args.category?.let { CategoryViewAction.DeleteCategory(it) }
                    ?.let { viewModel.dispatchViewAction(it) }
                findNavController().navigate(R.id.category_action_category_categoryfragment_to_category_categorylistfragment)
            }
            setNegativeButton(getString(R.string.category_text_set_negative_button_dialog)) { _, _ -> }
            setTitle(String.format(getString(R.string.category_text_set_title_dialog)))
            setMessage(String.format(getString(R.string.category_text_set_message_dialog),
                args.category?.name))
        }.create().show()
    }

    private fun validateFields(): Boolean {
        return (viewBinding.editTextNameCategory.text.toString().isNotEmpty()
                && viewBinding.rgCategoryType.isNotEmpty())
    }
}
