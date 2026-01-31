package com.example.androidsprint

import android.widget.Toast
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("recipes")
    fun getRecipes(): Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") id: Int): Call<Recipe>

    @GET("category/{id}/recipes")
    fun getRecipesByCategoryId(@Path("id") id: Int): Call<List<Recipe>>
}

private const val URL_RECIPE = "https://recipes.androidsprint.ru/api/"

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
            Toast.makeText(
                MainActivity()
                    .applicationContext, "$e", Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        return try {
            service.getRecipesByCategoryId(id).execute().body()
        } catch (e: Exception) {
            Toast.makeText(
                MainActivity().applicationContext, "$e", Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return try {
            service.getRecipeById(id).execute().body()
        } catch (e: Exception) {
            Toast.makeText(
                MainActivity().applicationContext, "$e", Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    fun getRecipesByIds(set: Set<String>): List<Recipe>? {
        return try {
            val ids = set.map { it.toInt() }.toSet()
            service.getRecipes().execute().body()?.filter { it.id in ids }
        } catch (e: Exception) {
            Toast.makeText(
                MainActivity().applicationContext, "$e", Toast.LENGTH_SHORT
            ).show()
            null
        }
    }
}
