package com.example.androidsprint.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.data.KEY_FAVORITE_PREFS
import com.example.androidsprint.data.KEY_PREFERENCE_FILE
import com.example.androidsprint.data.STUB
import com.example.androidsprint.model.Recipe

class FavoritesViewModel(application: Application): AndroidViewModel(application) {
    data class FavoritesListState(
        val recipe: Recipe? = null,
        val recipeList: List<Recipe>? = emptyList()
    )

    private val _recipeLiveData = MutableLiveData<FavoritesListState>()
    val recipeLiveData: LiveData<FavoritesListState> = _recipeLiveData

    fun loadRecipes(){
        val recipesList =  STUB.getRecipesByIds(
            getFavorites()
        )
        _recipeLiveData.value = FavoritesListState(
            recipeList = recipesList
        )
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