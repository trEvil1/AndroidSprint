package com.example.androidsprint

import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {
    @GET("category")
    suspend fun getCategories(): List<Category>

    @GET("recipe")
    suspend fun getRecipes(): List<Recipe>

    @GET("recipe/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategoryId(@Path("id") id: Int): List<Recipe>
}
