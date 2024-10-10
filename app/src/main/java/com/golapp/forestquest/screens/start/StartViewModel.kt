package com.golapp.forestquest.screens.start

import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.staff.PlayerClass
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

data class StartViewModel(
    private val playerDao: PlayerDao,
    private val itemsDao: ItemsDao
) : ContainerHost<StartState, StartSideEffect>, ViewModel() {

    override val container: Container<StartState, StartSideEffect> =
        container(
            initialState = StartState(
                players = emptyList(),
                selectedPlayer = null,
                inputFieldValue = "",
                playerClass = null
            )
        )

    //init { getPlayers() }

    @OptIn(OrbitExperimental::class)
    fun changeInputFieldValue(value: String) = blockingIntent {
        reduce {
            state.copy(
                inputFieldValue = value
            )
        }
    }

    fun changePlayerClass(playerClass: PlayerClass?) = intent {
        reduce {
            state.copy(
                playerClass = playerClass
            )
        }
    }

    fun selectPlayer(player: Player) = intent {
        reduce { state.copy(selectedPlayer = player) }
    }

    fun getPlayers() = intent {
        val players = playerDao.getAllPlayers()
        reduce { state.copy(players = players) }
    }

    fun insertPlayer(player: Player) = intent {
        playerDao.insertPlayer(player)
        getPlayers()
    }

    fun deletePlayer(player: Player) = intent {
        playerDao.deletePlayer(player)
        itemsDao.deleteItemsByPlayerId(player.id)
        reduce { state.copy(selectedPlayer = null) }
        getPlayers()
    }
}