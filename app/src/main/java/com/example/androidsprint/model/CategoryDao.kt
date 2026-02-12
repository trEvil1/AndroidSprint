package com.example.androidsprint.model

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter

@Dao
interface CategoryDao {
    @TypeConverter
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @TypeConverter
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Delete
    fun delete(category: Category)
}
