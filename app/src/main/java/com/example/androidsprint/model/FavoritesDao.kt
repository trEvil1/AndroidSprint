package com.example.androidsprint.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao{
    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    suspend fun getRecipesByFavorite(): List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun updateRecipe(recipes:Recipe)
}