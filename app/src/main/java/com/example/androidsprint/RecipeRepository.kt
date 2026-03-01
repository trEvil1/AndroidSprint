package com.example.androidsprint

import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.model.RecipesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RecipeRepository @Inject constructor(
    private val recipesDao: RecipesDao,
    private val categoryDao: CategoryDao,
    private val favoritesDao: FavoritesDao,
    private val service: RecipeApiService
) {

    private val ioDispatcher: CoroutineContext = Dispatchers.IO

    suspend fun getCategory(): List<Category>? {
        return withContext(ioDispatcher) {
            try {
                service.getCategories()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        return withContext(ioDispatcher) {
            try {
                service.getRecipesByCategoryId(id)
            } catch (e: Exception) {
                null
            }
        }

    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return withContext(ioDispatcher) {
            try {
                service.getRecipeById(id)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getCategoriesFromCache(): List<Category>? {
        return withContext(ioDispatcher) {
            try {
                categoryDao.getAll()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun insertCategories(categories: List<Category>) {
        withContext(ioDispatcher) { categoryDao.insertAll(categories) }

    }

    suspend fun insertRecipe(recipes: List<Recipe>) {
        withContext(ioDispatcher) { recipesDao.insertAll(recipes) }
    }

    suspend fun getRecipesFromCache(categoryId: Int): List<Recipe>? {
        return withContext(ioDispatcher) {
            try {
                recipesDao.getRecipesByCategoryId(categoryId)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getFavoriteFromCache(): List<Recipe>? {
        return withContext(ioDispatcher) {
            try {
                favoritesDao.getRecipesByFavorite()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun updateRecipe(recipe: Recipe) {
        withContext(ioDispatcher) {
            recipesDao.updateRecipe(recipe)
        }
    }
}