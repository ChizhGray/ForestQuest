package com.golapp.forestquest.room.interfaces

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.room.entities.Monster

@Dao
interface MonstersDao {
    @Query("SELECT id, name, image, lootMultiplier, current, max FROM ${DB.TAB_MONSTERS} where id = :playerId")
    suspend fun getMonstersBy(playerId: Int): List<Monster>

    @Insert
    suspend fun insertMonster(monster: Monster)

    @Update
    suspend fun updateMonster(monster: Monster)

    @Delete
    suspend fun deleteMonster(monster: Monster)
}