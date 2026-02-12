package com.example.androidsprint.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Serializable
@Parcelize
data class Category(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "category") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val imageUrl: String
) : Parcelable
