package com.example.androidsprint.ui.recipes.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeState(
        val isFavorite: Boolean,
        val portionCount: Int = 1,
        val recipe: Recipe? = null,
    )

    private val recipeRepository = RecipeRepository(getApplication())
    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            val currentPortions = _recipeLiveData.value?.portionCount ?: 1
            _recipeLiveData.postValue(
                RecipeState(
                    recipe = recipe,
                    isFavorite = recipe?.isFavorite ?: false,
                    portionCount = currentPortions,
                )
            )
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            val currentState = _recipeLiveData.value
            val recipe = currentState?.recipe ?:return@launch
            val newFavoriteState = !currentState.isFavorite
            recipeRepository.updateRecipe(
                recipe.copy(isFavorite = newFavoriteState)
            )
            _recipeLiveData.value = currentState.copy(isFavorite = newFavoriteState)
        }
    }


    fun onPortionsCountChanged(progress: Int) {
        val currentState = _recipeLiveData.value ?: return

        _recipeLiveData.value = currentState.copy(portionCount = progress)
    }
}