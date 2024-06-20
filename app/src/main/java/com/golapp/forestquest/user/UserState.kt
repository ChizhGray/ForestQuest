package com.golapp.forestquest.user

import com.golapp.forestquest.room.entities.User

data class UserState(
    val users: List<User>
)

sealed interface UserSideEffect
