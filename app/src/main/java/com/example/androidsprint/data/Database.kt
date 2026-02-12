package com.example.androidsprint.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidsprint.model.Category
import com.example.androidsprint.model.CategoryDao

@Database(entities = [Category::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
