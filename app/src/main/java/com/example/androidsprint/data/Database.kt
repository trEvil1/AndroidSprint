package com.example.androidsprint.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.model.RecipesDao

@Database(entities = [Category::class], version = 1)
abstract class DataBaseCategory : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}

@Database(entities = [Recipe::class], version = 1)
abstract class DataBaseRecipe: RoomDatabase(){
    abstract fun recipeDao(): RecipesDao
}