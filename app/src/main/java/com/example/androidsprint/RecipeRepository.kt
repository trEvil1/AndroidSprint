package com.example.androidsprint

import com.example.androidsprint.data.URL_RECIPE
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository {
    val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private val contentType = "application/json".toMediaType()
    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_RECIPE)
            .addConverterFactory(
                Json.asConverterFactory(contentType)
            ).build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategory(): List<Category>? {
        return try {
            service.getCategories().execute().body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        return try {
            service.getRecipesByCategoryId(id).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return try {
            service.getRecipeById(id).execute().body()
        } catch (e: Exception) {
            null
        }
    }

    fun getRecipesByIds(set: Set<String>): List<Recipe>? {
        return try {
            val ids = set.map { it.toInt() }.toSet()
            service.getRecipes().execute().body()?.filter { it.id in ids }
        } catch (e: Exception) {
            null
        }
    }
}