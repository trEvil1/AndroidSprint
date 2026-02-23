package com.example.androidsprint.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Recipe
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    data class FavoritesListState(
        val recipeList: List<Recipe>? = emptyList()
    )

    private val recipeRepository = RecipeRepository(getApplication())
    private val _favoriteLiveData = MutableLiveData(FavoritesListState())
    val favoriteLiveData: LiveData<FavoritesListState> = _favoriteLiveData

    fun loadRecipes() {
        viewModelScope.launch {
            val favorites = recipeRepository.getFavoriteFromCache()
            _favoriteLiveData.value = favoriteLiveData.value?.copy(recipeList = favorites)
        }
    }
}
