package com.example.androidsprint.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.model.Ingredient

class RecipeViewModel : ViewModel() {
    data class RecipeState(
        val recipeName: String? = null,
        val ingredients: List<Ingredient> = emptyList(),
        val isFavorite: Boolean? = null,
        val recipeId: Int? = null,
        val portionCount: Int? = null
    )
    private val application = Application().applicationContext
    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        _recipeLiveData.value = RecipeState(
            recipeId = recipeId
        )
        if (RecipeState().recipeName in getFavorites()) {
            _recipeLiveData.value = RecipeState(
                isFavorite = true
            )
        } else _recipeLiveData.value = RecipeState(
            isFavorite = false
        )
        TODO("load from network")
    }

    private fun getFavorites(): MutableSet<String> {
        val sp = application.getSharedPreferences(
            KEY_PREFERENCE_FILE, Context.MODE_PRIVATE
        )
        return HashSet(
            sp?.getStringSet(
                KEY_FAVORITE_PREFS, HashSet<String>()
            ) ?: mutableSetOf()
        )
    }

    private fun onFavoriteClicked(){
        RecipeFragment()
    }
}