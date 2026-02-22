package com.example.androidsprint

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidsprint.model.Recipe

@Dao
interface FavoritesDao{
    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    suspend fun getRecipesByFavorite(): List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipes:Recipe)
}