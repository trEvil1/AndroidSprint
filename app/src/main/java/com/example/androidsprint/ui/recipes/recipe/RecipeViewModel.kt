package com.example.androidsprint.ui.recipes.recipe

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
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    data class RecipeState(
        val isFavorite: Boolean = false,
        val portionCount: Int = 1,
        val recipe: Recipe? = null,
    )

    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)
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
            val recipe = currentState?.recipe ?: return@launch
            val newFavoriteState = !currentState.isFavorite
            repository.updateRecipe(
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