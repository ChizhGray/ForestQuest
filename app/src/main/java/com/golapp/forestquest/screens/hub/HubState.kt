package com.golapp.forestquest.screens.hub

import com.golapp.forestquest.room.entities.*

data class HubState(
    val player: Player,
    val items: List<Item>
)

sealed interface HubSideEffect
