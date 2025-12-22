package com.example.androidsprint.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint.model.Ingredient

class RecipeViewModel : ViewModel() {
    data class RecipeState(
        val recipeName: String? = null,
        val ingredients: List<Ingredient> = emptyList()
    )

    private val mutableSelectedItem = MutableLiveData<RecipeState>()
    var insideSelectedItem: LiveData<RecipeState> = mutableSelectedItem
    val outsideSelectedItem = insideSelectedItem

}