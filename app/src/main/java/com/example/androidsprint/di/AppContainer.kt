package com.example.androidsprint.di

import android.content.Context
import androidx.room.Room
import com.example.androidsprint.RecipeApiService
import com.example.androidsprint.RecipeRepository
import com.example.androidsprint.data.DataBase
import com.example.androidsprint.data.URL_RECIPE
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AppContainer(context: Context) {
    val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    private val dataBase = Room.databaseBuilder(
        context,
        DataBase::class.java,
        "database-category"
    ).fallbackToDestructiveMigration().build()

    private val recipesDao = dataBase.recipeDao()
    private val categoryDao = dataBase.categoryDao()
    val favoritesDao = dataBase.favoriteDao()
    private val ioDispatcher = Dispatchers.IO

    private val contentType = "application/json".toMediaType()
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_RECIPE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()

    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    val repository = RecipeRepository(
        recipesDao = recipesDao,
        categoryDao = categoryDao,
        favoritesDao = favoritesDao,
        ioDispatcher = ioDispatcher,
        service = service
    )

    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
    val recipeViewModelFactory = RecipeViewModelFactory(repository)
    val recipesListViewModelFactory = RecipesListViewModelFactory(repository)
    val favoritesViewModelFactory = FavoritesViewModelFactory(repository)
}