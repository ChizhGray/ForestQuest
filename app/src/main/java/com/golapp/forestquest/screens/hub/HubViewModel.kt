package com.golapp.forestquest.screens.hub

import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.ItemsDao
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

class HubViewModel(
    player: Player,
    private val itemsDao: ItemsDao
) : ContainerHost<HubState, HubSideEffect>, ViewModel() {
    override val container: Container<HubState, HubSideEffect> =
        container(
            initialState = HubState(
                player = player,
                items = emptyList()
            )
        )

    init {
        getUsersByPlayerId()
    }

    private fun getUsersByPlayerId() = intent {
        val users = itemsDao.getAllItemsByPlayerId(state.player.id)
        reduce { state.copy(items = users) }
    }

    fun getItems() = intent {
        val users = itemsDao.getAllItems()
        reduce { state.copy(items = users) }
    }

    fun insertItem(item: Item) = intent {
        itemsDao.insertItem(item)
        getUsersByPlayerId()
    }

    fun deleteItem(item: Item) = intent {
        itemsDao.deleteItem(item)
        getUsersByPlayerId()
    }
}
