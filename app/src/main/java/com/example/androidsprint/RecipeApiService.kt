package com.example.androidsprint

import com.example.androidsprint.model.Category
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>
}

class RecipeRepository() {

    private val contentType = "application/json".toMediaType()
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://recipes.androidsprint.ru/api/")
            .addConverterFactory(
                Json.asConverterFactory(contentType)
            ).build()

    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategory(): List<Category>? {
        val categoriesCall = service.getCategories()
        val categoriesResponse: Response<List<Category>?> = categoriesCall.execute()
        return categoriesResponse.body()
    }

}