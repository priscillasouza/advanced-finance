package com.advancedfinance.category.presentation.screen.category

import androidx.lifecycle.viewModelScope
import com.advancedfinance.category.R
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.model.CategoryModel
import com.advancedfinance.category.presentation.model.TransactionType
import com.advancedfinance.core.extensions.orZero
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: ICategoryRepository,
) : BaseViewModel<CategoryViewState, CategoryViewAction>() {

    private var category: CategoryModel? = null

    private val viewStateMutable = MutableStateFlow<CategoryViewState>(CategoryViewState.Loading)
    override val listViewState: StateFlow<CategoryViewState> = viewStateMutable

    override fun dispatchViewAction(viewAction: CategoryViewAction) {
        when (viewAction) {
            is CategoryViewAction.PreparedViewCategory -> {
                preparedView(viewAction.categoryModel)
            }
            is CategoryViewAction.SaveCategory -> {
                addOrUpdateCategory(viewAction.name, viewAction.typeTransaction)
            }
            is CategoryViewAction.DeleteCategory -> {
                deleteCategory(viewAction.categoryModel)
            }
        }
    }

    private fun preparedView(categoryModel: CategoryModel?) {
        if (categoryModel != null && categoryModel.id!! > 0) {
            this.category = categoryModel
            viewStateMutable.value = CategoryViewState.ViewUpdate(categoryModel)
        } else {
            viewStateMutable.value = CategoryViewState.ViewInsert
        }
    }

    private fun addOrUpdateCategory(
        name: String,
        typeTransaction: TransactionType,
    ) {
        category?.id?.let { id ->
            if (id > 0) {
                updateCategory(CategoryModel(
                    id = id,
                    name = name,
                    transactionType = typeTransaction))
            }
        } ?: addCategory(CategoryModel(
            id = null,
            name = name,
            transactionType = typeTransaction))
    }

    private fun updateCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                repository.updateCategory(categoryModel)
                viewStateMutable.value = CategoryViewState.SuccessUpdate
            } catch (e: Exception) {
                viewStateMutable.value =
                    CategoryViewState.Error(R.string.category_text_error_listing_categories)
            }
        }
    }

    private fun addCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                repository.saveCategory(categoryModel)
                viewStateMutable.value = CategoryViewState.SuccessInsert
            } catch (e: Exception) {
                viewStateMutable.value =
                    CategoryViewState.Error(R.string.category_text_error_adding_categories)
            }
        }
    }

    private fun deleteCategory(categoryModel: CategoryModel) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(categoryModel)
                viewStateMutable.value = CategoryViewState.SuccessDelete
            } catch (e: Exception) {
                viewStateMutable.value =
                    CategoryViewState.Error(R.string.category_text_error_delete_categories)
            }
        }
    }
}

sealed class CategoryViewAction {
    class PreparedViewCategory(val categoryModel: CategoryModel?) : CategoryViewAction()
    class SaveCategory(
        val name: String,
        val typeTransaction: TransactionType,
    ) : CategoryViewAction()
    class DeleteCategory(val categoryModel: CategoryModel) : CategoryViewAction()
}

sealed class CategoryViewState {
    object SuccessInsert : CategoryViewState()
    object SuccessUpdate : CategoryViewState()
    object SuccessDelete : CategoryViewState()
    data object Loading : CategoryViewState()
    class Error(val message: Int) : CategoryViewState()
    class ViewUpdate(val categoryModel: CategoryModel) : CategoryViewState()
    object ViewInsert : CategoryViewState()
}