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

    @field:TypeConverters(Converter::class)
    @ColumnInfo(name = "ingredients") val ingredients: List<Ingredient>,

    @field:TypeConverters(Converter::class)
    @ColumnInfo(name = "method") val method: List<String>,

    @ColumnInfo(name = "image") val imageUrl: String,
    @ColumnInfo(name = "categoryId") var categoryId: Int = 0,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
) : Parcelable