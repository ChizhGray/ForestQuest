package com.golapp.forestquest.koin

import android.content.Context
import androidx.room.*
import com.golapp.forestquest.AppDatabase

object DB {
    const val DB_NAME = "app_database"
    const val TAB_ITEMS = "items"
    const val TAB_PLAYERS = "players"
}

fun getDatabase(ctx: Context): AppDatabase {
    return Room.databaseBuilder(ctx, AppDatabase::class.java, DB.DB_NAME).build()
}