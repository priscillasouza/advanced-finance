package com.advancedfinance.category.presentation.screen.category_list

import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryListViewModel : BaseViewModel<CategoryListViewState, CategoryListViewAction>() {

    private val listViewStateMutable = MutableStateFlow<CategoryListViewState>(CategoryListViewState.Loading)
    override val listViewState: StateFlow<CategoryListViewState> = listViewStateMutable

    override fun dispatchViewAction(viewAction: CategoryListViewAction) {}
}

sealed class CategoryListViewAction {
    object GetListCategoryDetail : CategoryListViewAction()
}

sealed class CategoryListViewState {
    class Success(val listCategory: List<CategoryModel>) : CategoryListViewState()
    object Loading : CategoryListViewState()
    class Error(val message: String) : CategoryListViewState()
    object Empty : CategoryListViewState()
}