package com.golapp.forestquest.screens.start

import com.golapp.forestquest.room.entities.Player

data class StartState(
    val players: List<Player>,
    val selectedPlayer: Player?
)

sealed interface StartSideEffect {
    data object SimilarName: StartSideEffect
    data object PlayerCreated: StartSideEffect
}