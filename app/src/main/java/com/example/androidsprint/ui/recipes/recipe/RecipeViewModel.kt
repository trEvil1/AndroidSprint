package com.example.androidsprint.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.androidsprint.model.Ingredient

class RecipeViewModel: ViewModel() {
    data class RecipeState(
        val recipeName : String? = null,
        val ingredients: List<Ingredient> = emptyList()
    )
}