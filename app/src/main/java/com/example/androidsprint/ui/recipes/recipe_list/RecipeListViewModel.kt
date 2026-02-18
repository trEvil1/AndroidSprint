package com.example.androidsprint.ui.recipes.recipe_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.model.Recipe
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeListState(
        val recipesList: List<Recipe>? = null
    )

    private val recipeRepository = RecipeRepository(getApplication())
    private val _recipeListLiveData = MutableLiveData<RecipeListState>(RecipeListState())
    val recipeListLiveData: LiveData<RecipeListState> = _recipeListLiveData

    fun loadList(categoryId: Int) {
        viewModelScope.launch {
            val recipesFromCache = recipeRepository.getRecipesFromCache(categoryId)
            if (!recipesFromCache.isNullOrEmpty())
                _recipeListLiveData.value = RecipeListState(recipesFromCache)

            val recipesFromServer = recipeRepository.getRecipesByCategoryId(categoryId)
            if (!recipesFromServer.isNullOrEmpty()) {
                recipesFromServer.forEach { it.categoryId = categoryId }
                recipeRepository.insertRecipe(recipesFromServer)
                _recipeListLiveData.value = RecipeListState(recipesFromServer)
            }
        }
    }
}
