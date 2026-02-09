package com.example.androidsprint

import androidx.room.Room
import com.example.androidsprint.data.URL_RECIPE
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.DataBase
import com.example.androidsprint.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class RecipeRepository {
    private val contentType = "application/json".toMediaType()
    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_RECIPE)
            .addConverterFactory(
                Json.asConverterFactory(contentType)
            ).build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    val dataBase = Room.databaseBuilder(
        MainActivity().applicationContext,
        DataBase::class.java,
        "database-category"
    ).build()

    suspend fun getCategory(): List<Category>? {
        return withContext(Dispatchers.IO) {
            try {
                service.getCategories()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                service.getRecipesByCategoryId(id)
            } catch (e: Exception) {
                null
            }
        }

    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                service.getRecipeById(id)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getRecipesByIds(set: Set<String>): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val ids = set.map { it.toInt() }.toSet()
                service.getRecipes().filter { it.id in ids }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getCategoriesFromCache(): List<Category>? {
        return withContext(Dispatchers.IO) {
            try {
                dataBase.categoryDao().getAll()
            } catch (e: Exception) {
                null
            }
        }
    }
}