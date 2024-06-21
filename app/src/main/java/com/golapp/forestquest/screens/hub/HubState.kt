package com.golapp.forestquest.screens.hub

import com.golapp.forestquest.room.entities.User

data class HubState(
    val users: List<User>
)

sealed interface HubSideEffect
