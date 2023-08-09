package com.advancedfinance.category.presentation.screen.category_list

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.advancedfinance.category.databinding.CategoryFragmentListDetailBinding
import com.advancedfinance.category.presentation.adapter.CategoryListAdapter
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.platform.BaseFragment
import kotlinx.coroutines.launch

class CategoryListDetailFragment(private val categoryType: Int) :
    BaseFragment<CategoryFragmentListDetailBinding, CategoryListDetailViewModel>(
        CategoryFragmentListDetailBinding::inflate,
        CategoryListDetailViewModel::class
    ) {

    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun prepareView(savedInstanceState: Bundle?) {
        dispatchByCategoryType(categoryType)
        settingObservable()
        setAdapterListCategoryDetail()
    }

    private fun dispatchByCategoryType(categoryType: Int) {
        viewModel.dispatchViewAction(CategoryListDetailViewAction.GetListCategoryType(categoryType))
    }

    private fun settingObservable() {
        lifecycleScope.launch {
            viewModel.listViewState.collect {
                when (it) {
                    is CategoryListDetailViewState.Loading -> showLoading()
                    is CategoryListDetailViewState.Error -> showError(it.message)
                    is CategoryListDetailViewState.Success -> listAdapterCategoryDetail(it.listCategory)
                    else -> {}
                }
            }
        }
    }

    private fun setAdapterListCategoryDetail() {
        viewBinding.recyclerViewDetailCategoryList.apply {
            categoryListAdapter = CategoryListAdapter() { category ->
                val action =
                    CategoryListFragmentDirections.categoryActionCategoryCategorylistfragmentToCategoryCategoryfragment(
                        category)
                findNavController().navigate(action)
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = categoryListAdapter
        }
    }

    private fun listAdapterCategoryDetail(list: List<CategoryModel>) {
        categoryListAdapter.setList(list)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message,
            Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {}
}