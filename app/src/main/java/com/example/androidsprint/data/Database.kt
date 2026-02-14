package com.example.androidsprint.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao
import com.example.androidsprint.model.Ingredient
import com.example.androidsprint.model.Recipe
import com.example.androidsprint.model.RecipesDao
import com.google.gson.Gson
import java.util.Arrays.asList


@Database(entities = [Category::class, Recipe::class], version = 1)
class RecipeAppDatabase {

    @TypeConverters(Converter::class)
    abstract class DataBaseCategory : RoomDatabase() {
        abstract fun categoryDao(): CategoryDao
        abstract fun recipeDao(): RecipesDao
    }
}

private class Converter {
    @TypeConverter
    fun fromIngredientsToJson(value: List<Ingredient>): List<String> {
        val json = Gson().toJson(value)
     return Gson().fromJson<List<String>>(json, Array<String>::class.java)
    }

    @TypeConverter
    fun jsonToRecipeList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()

}
