package com.example.androidsprint.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
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

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg category: Category)

    @Delete
    fun delete(category: Category)
}

@Database(entities = [Category::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}