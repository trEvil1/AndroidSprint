package com.example.androidsprint.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint.model.Ingredient

class RecipeViewModel : ViewModel() {
    data class RecipeState(
        val recipeName: String? = null,
        val ingredients: List<Ingredient> = emptyList(),
        val isFavorite: Boolean? = null
    )

    private val _recipeLiveData = MutableLiveData<RecipeState>()
    val recipeLiveData : LiveData<RecipeState> = _recipeLiveData

   init {
       Log.d("INIT", "111111111111111111")
       _recipeLiveData.value = RecipeState(
           isFavorite = true
       )
   }
}