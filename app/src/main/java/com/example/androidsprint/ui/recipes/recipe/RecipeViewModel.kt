package com.example.androidsprint.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.data.STUB
import com.example.androidsprint.model.Recipe

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeState(
        val isFavorite: Boolean = false,
        val portionCount: Int = 1,
        val recipe: Recipe? = null,
        val recipeImage: Drawable? = null
    )

    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData: LiveData<RecipeState> = _recipeLiveData

    fun loadRecipe(recipeId: Int) {
        val recipeImage =
            _recipeLiveData.value?.recipe?.imageUrl?.let {
                getApplication<Application>().applicationContext.assets.open(
                    it
                )
            }

        val drawable = Drawable.createFromStream(recipeImage, null)
        val recipe = STUB.getRecipeById(recipeId)
        val favorites = getFavorites()
        val currentPortions = _recipeLiveData.value?.portionCount ?: 1
        _recipeLiveData.value = RecipeState(
            recipe = recipe,
            isFavorite = recipe.id.toString() in favorites,
            portionCount = currentPortions,
            recipeImage = drawable
        )
        if (recipeImage == null) {
            Log.e("ERROR", "Image is null")
            return
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
        val favorites = getFavorites()
        val currentState = _recipeLiveData.value ?: return
        val newFavoriteState = !(currentState.isFavorite)
        _recipeLiveData.value = currentState.copy(isFavorite = newFavoriteState)
        if (newFavoriteState)
            favorites.add(currentState.recipe?.id.toString())
        else favorites.remove(currentState.recipe?.id.toString())
        saveFavorite(favorites)
        _recipeLiveData.value = currentState.copy(isFavorite = newFavoriteState)
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

    fun onPortionsCountChanged(progress: Int) {
        val currentState = _recipeLiveData.value ?: return

        _recipeLiveData.value = currentState.copy(portionCount = progress)
    }
}