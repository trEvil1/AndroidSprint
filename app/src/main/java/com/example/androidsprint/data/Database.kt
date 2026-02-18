package com.example.androidsprint.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.model.RecipesDao

@Database(entities = [Category::class, Recipe::class], version = 1)

@TypeConverters(Converter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipesDao
}


