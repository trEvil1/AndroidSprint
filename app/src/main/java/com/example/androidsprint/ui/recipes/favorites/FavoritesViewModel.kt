package com.example.androidsprint.ui.recipes.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {
    data class FavoritesListState(
        val recipeList: List<Recipe>? = emptyList()
    )

    private val _favoriteLiveData = MutableLiveData(FavoritesListState())
    val favoriteLiveData: LiveData<FavoritesListState> = _favoriteLiveData

    fun loadRecipes() {
        viewModelScope.launch {
            val favorites = repository.getFavoriteFromCache()
            _favoriteLiveData.value = favoriteLiveData.value?.copy(recipeList = favorites)
        }
    }
}
