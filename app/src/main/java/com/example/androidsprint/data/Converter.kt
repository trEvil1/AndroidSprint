package com.example.androidsprint.data

import androidx.room.TypeConverter
import com.example.androidsprint.model.Ingredient
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun fromIngredientsToJson(value: List<Ingredient>): List<String> {
        val json = Gson().toJson(value)
        return Gson().fromJson<List<String>>(json, Array<String>::class.java)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()

}
