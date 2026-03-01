package com.example.androidsprint.di

import android.content.Context
import androidx.room.Room
import com.example.androidsprint.FavoritesDao
import com.example.androidsprint.RecipeApiService
import com.example.androidsprint.data.DataBase
import com.example.androidsprint.data.URL_RECIPE
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.RecipesDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(
            context,
            DataBase::class.java,
            "database-category"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCategoriesDao(database: DataBase): CategoryDao = database.categoryDao()

    @Provides
    fun provideRecipeDao(database: DataBase): RecipesDao = database.recipeDao()

    @Provides
    fun provideFavoritesDao(database: DataBase): FavoritesDao = database.favoriteDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()

    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(URL_RECIPE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }
}