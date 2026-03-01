package com.example.androidsprint.di

import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.ui.recipes.recipe_list.RecipeListViewModel

class RecipesListViewModelFactory(private val repository: RecipeRepository) :
    Factory<RecipeListViewModel> {
    override fun create(): RecipeListViewModel {
        return RecipeListViewModel(repository)
    }
}