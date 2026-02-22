package com.example.androidsprint

import android.content.Context
import androidx.room.Room
import com.example.androidsprint.data.DataBase
import com.example.androidsprint.data.URL_RECIPE
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class RecipeRepository(private val context: Context) {
    private val contentType = "application/json".toMediaType()
    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_RECIPE)
            .addConverterFactory(
                Json.asConverterFactory(contentType)
            ).build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    private val dataBase = Room.databaseBuilder(
        context,
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

    suspend fun insertCategories(categories: List<Category>) {
        withContext(Dispatchers.IO) { dataBase.categoryDao().insertAll(categories) }

    }

    suspend fun insertRecipe(recipes: List<Recipe>) {
        withContext(Dispatchers.IO) { dataBase.recipeDao().insertAll(recipes) }
    }

    suspend fun getRecipesFromCache(categoryId: Int): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                dataBase.recipeDao().getRecipesByCategoryId(categoryId)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getFavoriteFromCache(): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                dataBase.favoriteDao().getRecipesByFavorite()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun updateRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            dataBase.recipeDao().updateRecipe(recipe)
        }
    }
}