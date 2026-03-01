package com.example.androidsprint.di

import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.ui.categories.CategoriesListViewModel

class CategoriesListViewModelFactory(
    private val repository: RecipeRepository
) :
    Factory<CategoriesListViewModel> {
    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(repository)
    }
}