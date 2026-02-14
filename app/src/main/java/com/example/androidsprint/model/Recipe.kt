package com.example.androidsprint.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidsprint.data.Converter
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Serializable
@Parcelize
data class Recipe(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recipe") val title: String,
    @ColumnInfo(name = "ingredients")
    @field:TypeConverters(Converter::class) val ingredients: List<Ingredient>,
    @ColumnInfo(name = "method")
    @field:TypeConverters(Converter::class) val method: List<String>,
    @ColumnInfo(name = "image") val imageUrl: String,
    @ColumnInfo(name = "categoryId") val categoryId: Int
) : Parcelable