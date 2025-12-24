package com.example.androidsprint.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.data.STUB
import com.example.androidsprint.model.Ingredient

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeState(
        val recipeName: String? = null,
        val ingredients: List<Ingredient> = emptyList(),
        val isFavorite: Boolean? = null,
        val recipeId: Int? = null,
        val portionCount: Int? = null
    )

    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val favorites = getFavorites()
        val currentPortions = _recipeLiveData.value?.portionCount ?: 1
        _recipeLiveData.value = RecipeState(
            recipeName = recipe.title,
            ingredients = recipe.ingredients,
            isFavorite = recipe.id.toString() in favorites,
            recipeId = recipe.id,
            portionCount = currentPortions
        )
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
        val favorites = getFavorites()
        val currentState = _recipeLiveData.value ?: return
        _recipeLiveData.value = currentState.copy(isFavorite = !(currentState.isFavorite ?: false))
        if (_recipeLiveData.value?.isFavorite == true)
            favorites.add(_recipeLiveData.value?.recipeId.toString())
        else favorites.remove(_recipeLiveData.value?.recipeId.toString())
        saveFavorite(favorites)
    }

    private fun saveFavorite(set: Set<String>) {
        val sharedPreferences: SharedPreferences =
            getApplication<Application>().applicationContext.getSharedPreferences(
                KEY_PREFERENCE_FILE, Context.MODE_PRIVATE
            ) ?: return
        sharedPreferences.edit {
            putStringSet(KEY_FAVORITE_PREFS, set)
        }
    }
}