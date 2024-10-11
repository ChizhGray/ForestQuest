package com.golapp.forestquest.room.entities

import androidx.room.*
import com.golapp.forestquest.koin.DB

@Entity(tableName = DB.TAB_ITEMS)
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerId: Int,
    val name: String,
    val description: String,
    val itemType: String,
    val icon: Int
)