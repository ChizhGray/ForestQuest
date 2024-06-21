package com.golapp.forestquest

import androidx.room.*
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*

@Database(
    entities = [
        User::class,
        Player::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playerDao(): PlayerDao
}