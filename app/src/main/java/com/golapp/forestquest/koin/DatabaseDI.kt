package com.golapp.forestquest.koin

import android.content.Context
import androidx.room.*
import com.golapp.forestquest.AppDatabase

object Constants {
    const val APP_DATABASE= "app_database"
}

fun getDatabase(ctx: Context): AppDatabase {
    return Room.databaseBuilder(ctx, AppDatabase::class.java, Constants.APP_DATABASE).build()
}

fun provideDao(db: AppDatabase) = db.userDao()