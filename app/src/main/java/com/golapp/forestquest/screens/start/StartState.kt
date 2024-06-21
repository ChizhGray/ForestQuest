package com.golapp.forestquest.screens.start

import com.golapp.forestquest.room.entities.Player

data class StartState(
    val players: List<Player>
)

sealed interface StartSideEffect