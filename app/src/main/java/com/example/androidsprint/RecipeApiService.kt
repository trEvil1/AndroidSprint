package com.example.androidsprint

import android.widget.Toast
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.google.gson.Gson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("recipes")
    fun getRecipes(): Call<List<Recipe>>

    @GET("recipe")
    fun getRecipe(): Call<Recipe>
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

    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

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
            val retrofit =
                Retrofit.Builder()
                    .baseUrl("https://recipes.androidsprint.ru/api/category/$id/")
                    .addConverterFactory(
                        Json.asConverterFactory(contentType)
                    ).build()

            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

            service.getRecipes().execute().body()
        } catch (e: Exception) {
            Toast.makeText(
                MainActivity().applicationContext, "$e", Toast.LENGTH_SHORT
            ).show()
            null
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return try {
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/recipe/$id")
                .build()
            client.newCall(request).execute().use { response ->
                val json = response.body?.string()
                Gson().fromJson(json, Recipe::class.java)
            }
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
