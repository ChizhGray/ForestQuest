package com.golapp.forestquest.screens.start

import com.golapp.forestquest.room.entities.Player
import com.golapp.forestquest.staff.PlayerClass

data class StartState(
    val players: List<Player>,
    val selectedPlayer: Player?,
    val inputFieldValue: String,
    val playerClass: PlayerClass?
)

sealed interface StartSideEffect {
    data object SimilarName: StartSideEffect
    data object PlayerCreated: StartSideEffect
}