package com.example.androidsprint.di

import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(private val repository: RecipeRepository): Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(repository)
    }
}