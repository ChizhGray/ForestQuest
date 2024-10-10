package com.golapp.forestquest.room.interfaces

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.room.entities.*

@Dao
interface PlayerDao {
    @Query("SELECT id, name, classOfPlayer, attack, defence FROM ${DB.TAB_PLAYERS}")
    suspend fun getAllPlayers(): List<Player>

    @Insert
    suspend fun insertPlayer(user: Player)

    @Update
    suspend fun updatePlayer(user: Player)

    @Delete
    suspend fun deletePlayer(user: Player)
}