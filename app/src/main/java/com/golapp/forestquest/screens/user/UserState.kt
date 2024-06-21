package com.golapp.forestquest.screens.user

import com.golapp.forestquest.room.entities.User

data class UserState(
    val users: List<User>
)

sealed interface UserSideEffect
