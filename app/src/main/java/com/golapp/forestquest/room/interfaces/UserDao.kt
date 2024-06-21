package com.golapp.forestquest.room.interfaces

import androidx.room.*
import com.golapp.forestquest.koin.DB
import com.golapp.forestquest.room.entities.User

@Dao
interface UserDao {
    @Query("SELECT id, name, age FROM ${DB.TAB_USERS}")
    suspend fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}
