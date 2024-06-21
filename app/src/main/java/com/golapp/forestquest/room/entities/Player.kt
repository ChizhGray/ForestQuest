package com.golapp.forestquest.room.entities

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.staff.PlayerClass
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = DB.TAB_PLAYERS)
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val classOfPlayer: String
)