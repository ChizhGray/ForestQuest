package com.golapp.forestquest

import androidx.room.*
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*

@Database(
    entities = [
        Item::class,
        Player::class,
        Monster::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
    abstract fun playerDao(): PlayerDao
    abstract fun monstersDao(): MonstersDao
}