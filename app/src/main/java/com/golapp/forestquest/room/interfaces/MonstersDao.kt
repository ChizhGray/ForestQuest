package com.golapp.forestquest.room.interfaces

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.room.entities.Monster

@Dao
interface MonstersDao {
    @Query("SELECT id, name, ownerId, image, lootMultiplier, current, max FROM ${DB.TAB_MONSTERS}")
    suspend fun getMonsters(): List<Monster>

    @Insert
    suspend fun insertMonster(user: Monster)

    @Delete
    suspend fun deleteMonster(user: Monster)
}