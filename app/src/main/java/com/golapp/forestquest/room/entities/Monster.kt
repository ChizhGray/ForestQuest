package com.golapp.forestquest.room.entities

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.staff.Durability

@Entity(tableName = DB.TAB_MONSTERS)
data class Monster(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ownerId: Int,
    val name: String,
    val image: Int,
    @Embedded val health: Durability,
    val lootMultiplier: Int
)