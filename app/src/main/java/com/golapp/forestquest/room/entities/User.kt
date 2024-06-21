package com.golapp.forestquest.room.entities

import androidx.room.*
import com.golapp.forestquest.koin.DB

@Entity(tableName = DB.TAB_USERS)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int
)