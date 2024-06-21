package com.golapp.forestquest.screens.start

import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.PlayerDao
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

data class StartViewModel(
    private val playerDao: PlayerDao
) : ContainerHost<StartState, StartSideEffect>, ViewModel() {

    override val container: Container<StartState, StartSideEffect> =
        container(
            initialState = StartState(
                players = emptyList()
            )
        )

    init {
        getPlayers()
    }

    private fun getPlayers() = intent {
        val players = playerDao.getAllPlayers()
        reduce { state.copy(players = players) }
    }

    fun insertPlayer(player: Player) = intent {
        playerDao.insertPlayer(player)
        getPlayers()
    }

    fun deletePlayer(player: Player) = intent {
        playerDao.deletePlayer(player)
        getPlayers()
    }
}