package com.advancedfinance.category.di

import com.advancedfinance.category.data.mapper.MapEntityToModel
import com.advancedfinance.category.data.mapper.MapModelToEntity
import com.advancedfinance.category.data.repository.CategoryRepository
import com.advancedfinance.category.domain.ICategoryRepository
import com.advancedfinance.category.presentation.screen.category.CategoryViewModel
import com.advancedfinance.category.presentation.screen.category_list.CategoryListDetailViewModel
import com.advancedfinance.category.presentation.screen.category_list.CategoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val categoryModule = module {

    viewModel {
        CategoryViewModel(get(named("CategoryRepository")))
    }

    viewModel {
        CategoryListViewModel()
    }

    viewModel {
        CategoryListDetailViewModel(get(named("CategoryRepository")))
    }

    single<ICategoryRepository>(named("CategoryRepository")) {
        CategoryRepository(get(), get(), get())
    }

    single { MapModelToEntity() }

    single { MapEntityToModel() }
}