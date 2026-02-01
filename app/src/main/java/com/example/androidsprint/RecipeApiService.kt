package com.example.androidsprint

import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("recipe")
    fun getRecipes(): Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") id: Int): Call<Recipe>

    @GET("category/{id}/recipes")
    fun getRecipesByCategoryId(@Path("id") id: Int): Call<List<Recipe>>
}
