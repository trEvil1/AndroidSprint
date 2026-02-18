package com.example.androidsprint.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.model.Recipe
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    data class FavoritesListState(
        val recipeList: List<Recipe>? = emptyList()
    )

    private val recipeRepository = RecipeRepository(getApplication())
    private val _favoriteLiveData = MutableLiveData<FavoritesListState>()
    val favoriteLiveData: LiveData<FavoritesListState> = _favoriteLiveData

    fun loadRecipes() {
        viewModelScope.launch {
            val favoritesFromCache = recipeRepository.getFavoriteFromCache()
            _favoriteLiveData.value =
                _favoriteLiveData.value?.copy(recipeList = favoritesFromCache)
            val favoritesFromServer = recipeRepository.getRecipesByIds(getFavorites())
            if (favoritesFromCache == null) {
                recipeRepository.insertFavorites(favoritesFromServer ?: return@launch)
                _favoriteLiveData.value =
                    _favoriteLiveData.value?.copy(recipeList = favoritesFromServer)
            }

        }
    }

    fun getFavorites(): MutableSet<String> {
        val sp = getApplication<Application>().applicationContext.getSharedPreferences(
            KEY_PREFERENCE_FILE, Context.MODE_PRIVATE
        )
        return HashSet(
            sp?.getStringSet(
                KEY_FAVORITE_PREFS, HashSet<String>()
            ) ?: mutableSetOf()
        )
    }
}