package com.advancedfinance.category.presentation.screen.category_list

import androidx.lifecycle.viewModelScope
import com.advancedfinance.category.R
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoryListDetailViewModel(
    private val repository: ICategoryRepository
) : BaseViewModel<CategoryListDetailViewState, CategoryListDetailViewAction>() {

    private val listViewStateMutable =
        MutableStateFlow<CategoryListDetailViewState>(CategoryListDetailViewState.Loading)
    override val listViewState: StateFlow<CategoryListDetailViewState> = listViewStateMutable

    override fun dispatchViewAction(viewAction: CategoryListDetailViewAction) {
        when (viewAction) {
            is CategoryListDetailViewAction.GetListCategoryType -> getListCategoryType(viewAction.typeCategory)
        }
    }

    private fun getListCategoryType(typeCategory: Int) {
        viewModelScope.launch {
            listViewStateMutable.value = CategoryListDetailViewState.Loading
            repository.getCategoryType(typeCategory)
                .catch { exception ->
                    exception.printStackTrace()
                    listViewStateMutable.value = CategoryListDetailViewState.Error(R.string.category_text_error_listing_categories.toString())
                }.collect {
                    if (it.isEmpty()) {
                        listViewStateMutable.value = CategoryListDetailViewState.Empty
                    }
                    listViewStateMutable.value = CategoryListDetailViewState.Success(it)
                }
        }
    }
}

sealed class CategoryListDetailViewAction {
    class GetListCategoryType(val typeCategory: Int) : CategoryListDetailViewAction()
}

sealed class CategoryListDetailViewState {
    class Success(val listCategory: List<CategoryModel>) : CategoryListDetailViewState()
    object Loading : CategoryListDetailViewState()
    class Error(val message: String) : CategoryListDetailViewState()
    object Empty : CategoryListDetailViewState()
}