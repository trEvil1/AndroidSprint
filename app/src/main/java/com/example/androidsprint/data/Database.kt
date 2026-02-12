package com.example.androidsprint.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.model.RecipesDao
import com.google.gson.Gson


@Database(entities = [Category::class, Recipe::class], version = 1)
class RecipeAppDatabase {

    @TypeConverters(ConverterCategory::class)
    abstract class DataBaseCategory : RoomDatabase() {
        abstract fun categoryDao(): CategoryDao
    }

    @TypeConverters(ConverterRecipe::class)
    abstract class DataBaseRecipe : RoomDatabase() {
        abstract fun recipeDao(): RecipesDao
    }
}

private class ConverterRecipe {
    @TypeConverter
    fun fromRecipeListToJson(value: List<Recipe>): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToRecipeList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()
}

private class ConverterCategory {
    @TypeConverter
    fun fromRecipeListToJson(value: List<Category>): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToRecipeList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()
}
