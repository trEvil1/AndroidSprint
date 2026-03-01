package com.example.androidsprint.ui.recipes.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Recipe
import kotlinx.coroutines.launch

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {
    data class RecipeListState(
        val recipesList: List<Recipe>? = null
    )

    private val _recipeListLiveData = MutableLiveData<RecipeListState>(RecipeListState())
    val recipeListLiveData: LiveData<RecipeListState> = _recipeListLiveData

    fun loadList(categoryId: Int) {
        viewModelScope.launch {
            val recipesFromCache = repository.getRecipesFromCache(categoryId)
            if (!recipesFromCache.isNullOrEmpty())
                _recipeListLiveData.value = RecipeListState(recipesFromCache)

            val recipesFromServer = repository.getRecipesByCategoryId(categoryId)
            if (!recipesFromServer.isNullOrEmpty()) {
                recipesFromServer.forEach { it.categoryId = categoryId }
                repository.insertRecipe(recipesFromServer)
                _recipeListLiveData.value = RecipeListState(recipesFromServer)
            }
        }
    }
}
