package com.golapp.forestquest

import androidx.room.*
import com.golapp.forestquest.room.entities.User
import com.golapp.forestquest.room.interfaces.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}