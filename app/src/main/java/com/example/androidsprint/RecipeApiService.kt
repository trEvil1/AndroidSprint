package com.example.androidsprint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    @GET("recipe")
    fun getRecipe(): Call<Recipe>
}

class RecipeRepository : ViewModel() {

    private var categoriesList: MutableLiveData<List<Category>>? = null
    private var recipesListByCategoryId: MutableLiveData<List<Recipe>>? = null
    private var recipesListByIds: MutableLiveData<List<Recipe>>? = null
    private var recipeById: MutableLiveData<Recipe>? = null


    private val threadPool = Executors.newFixedThreadPool(10)

    private val contentType = "application/json".toMediaType()

    fun getCategory(): List<Category>? {
        threadPool.execute {
            try {
                val retrofit: Retrofit =
                    Retrofit.Builder()
                        .baseUrl("https://recipes.androidsprint.ru/api/")
                        .addConverterFactory(
                            Json.asConverterFactory(contentType)
                        ).build()

                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)
                val categoriesCall = service.getCategories()
                val categoriesResponse: Response<List<Category>?> = categoriesCall.execute()
                categoriesList?.value = categoriesResponse.body()
            } catch (e: Exception) {

            }
        }
        return categoriesList?.value
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        threadPool.execute {
            try {
                val retrofit: Retrofit =
                    Retrofit.Builder()
                        .baseUrl("https://recipes.androidsprint.ru/api/category/$id/")
                        .addConverterFactory(
                            Json.asConverterFactory(contentType)
                        ).build()

                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

                val recipeCall = service.getRecipes()
                val recipeResponse: Response<List<Recipe>?>? = recipeCall.execute()
                recipesListByCategoryId?.value = recipeResponse?.body()
            } catch (e: Exception) {

            }
        }
        return recipesListByCategoryId?.value
    }

    fun getRecipeById(id: Int): Recipe? {
        threadPool.execute {
            try {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/recipe/$id")
                    .build()
                client.newCall(request).execute().use { response ->
                    val json = response.body?.string()
                    recipeById?.value = Gson().fromJson(json, Recipe::class.java)
                }
            } catch (e: Exception) {

            }
        }
        return recipeById?.value
    }

    fun getRecipesByIds(set: Set<String>): List<Recipe>? {
        threadPool.execute {
            try {
                val retrofit: Retrofit =
                    Retrofit.Builder()
                        .baseUrl("https://recipes.androidsprint.ru/api/")
                        .addConverterFactory(
                            Json.asConverterFactory(contentType)
                        ).build()

                val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

                val recipeCall = service.getRecipes()
                val recipeResponse: Response<List<Recipe>?>? = recipeCall.execute()
                val ids = set.map { it.toInt() }.toSet()
                recipesListByIds?.value = recipeResponse?.body()?.filter { it.id in ids }
            } catch (e: Exception) {

            }
        }
        return recipesListByIds?.value
    }
}
