package com.example.androidsprint

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.google.gson.Gson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.concurrent.Executors

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("recipes")
    fun getRecipes(): Call<List<Recipe>>

}

class RecipeRepository() {

    private var categoriesList: List<Category>? = emptyList()
    private var recipeList: List<Recipe>? = emptyList()
    private var recipe: Recipe? = null
    private val threadPool = Executors.newFixedThreadPool(10)

    private val contentType = "application/json".toMediaType()


    fun getCategory(): List<Category>? {
        threadPool.execute {
            val retrofit: Retrofit =
                Retrofit.Builder()
                    .baseUrl("https://recipes.androidsprint.ru/api/")
                    .addConverterFactory(
                        Json.asConverterFactory(contentType)
                    ).build()

            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
            val categoriesCall = service.getCategories()
            val categoriesResponse: Response<List<Category>?> = categoriesCall.execute()
            categoriesList = categoriesResponse.body()
        }
        return categoriesList
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        threadPool.execute {
            val retrofit: Retrofit =
                Retrofit.Builder()
                    .baseUrl("https://recipes.androidsprint.ru/api/category/$id/")
                    .addConverterFactory(
                        Json.asConverterFactory(contentType)
                    ).build()

            val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

            val recipeCall = service.getRecipes()
            val recipeResponse: Response<List<Recipe>?>? = recipeCall.execute()
            recipeList = recipeResponse?.body()
        }
        return recipeList
    }

    fun getRecipeById(id: Int): Recipe? {
        threadPool.execute {
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/recipe/$id")
                .build()
            client.newCall(request).execute().use { response ->
                val json = response.body?.string()
                recipe = Gson().fromJson(json, Recipe::class.java)
            }
        }
        return recipe
    }

    fun getRecipesByIds(set: Set<String>){
        val ids = set.map { it.toInt() }.toSet()
    }

}


