package com.example.androidsprint.ui.recipes.recipe

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

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeState(
        val isFavorite: Boolean = false,
        val portionCount: Int = 1,
        val recipe: Recipe? = null,
    )

    private val recipeRepository = RecipeRepository(getApplication())
    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            val favorites = getFavorites()
            val currentPortions = _recipeLiveData.value?.portionCount ?: 1
            _recipeLiveData.postValue(
                RecipeState(
                    recipe = recipe,
                    isFavorite = recipe?.id.toString() in favorites,
                    portionCount = currentPortions,
                )
            )
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sp = getApplication<Application>().applicationContext.getSharedPreferences(
            KEY_PREFERENCE_FILE, Context.MODE_PRIVATE
        )
        return HashSet(
            sp?.getStringSet(
                KEY_FAVORITE_PREFS, HashSet<String>()
            ) ?: mutableSetOf()
        )
    }

    fun onFavoriteClicked() {
        val currentState = _recipeLiveData.value ?: return
        val recipe = currentState.recipe ?: return
        val newFavoriteState = currentState.isFavorite

        viewModelScope.launch {
            recipeRepository.updateRecipe(
                recipe.copy(isFavorite = newFavoriteState)
            )
        }
        _recipeLiveData.value = currentState.copy(isFavorite = newFavoriteState)
    }


    fun onPortionsCountChanged(progress: Int) {
        val currentState = _recipeLiveData.value ?: return

        _recipeLiveData.value = currentState.copy(portionCount = progress)
    }
}