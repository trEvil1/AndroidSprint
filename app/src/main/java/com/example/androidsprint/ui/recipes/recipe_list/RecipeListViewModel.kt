package com.example.androidsprint.ui.recipes.recipe_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.data.STUB
import com.example.androidsprint.model.Recipe

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeListState(
        val category: List<Recipe>? = null
    )

    private val _recipeListLiveData = MutableLiveData<RecipeListState>()
    val recipeListLiveData: LiveData<RecipeListState> = _recipeListLiveData
    fun loadList(categoryId: Int) {
        val category = STUB.getRecipesByCategoryId(categoryId)

        _recipeListLiveData.value = RecipeListState(
            category = category
        )
    }
}