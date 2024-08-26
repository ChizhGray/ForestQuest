package com.golapp.forestquest.room.interfaces

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.room.entities.Item

@Dao
interface ItemsDao {
    @Query("SELECT id, name, age, playerId FROM ${DB.TAB_ITEMS} where playerId = :playerId")
    suspend fun getAllItemsByPlayerId(playerId: Int): List<Item>

    @Query("SELECT id, name, age, playerId FROM ${DB.TAB_ITEMS}")
    suspend fun getAllItems(): List<Item>

    @Insert
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("DELETE FROM ${DB.TAB_ITEMS} WHERE playerId = :playerId")
    suspend fun deleteItemsByPlayerId(playerId: Int)
}
