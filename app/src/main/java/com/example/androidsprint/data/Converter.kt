package com.example.androidsprint.data

import androidx.room.TypeConverter
import com.example.androidsprint.model.Ingredient
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun fromStringListToString(value: List<String>): String? = Gson().toJson(value)

    @TypeConverter
    fun fromIngredientsToJson(value: List<Ingredient>): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String): List<String> =
        Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun jsonToIngredients(value: String): List<Ingredient> =
        Gson().fromJson(value, Array<Ingredient>::class.java).toList()
}