package com.example.androidsprint.di

import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(private val repository: RecipeRepository) : Factory<RecipeViewModel> {
    override fun create(): RecipeViewModel {
        return RecipeViewModel(repository)
    }
}