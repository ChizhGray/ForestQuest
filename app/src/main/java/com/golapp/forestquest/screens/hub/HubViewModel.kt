package com.golapp.forestquest.screens.hub

import android.util.Log
import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.staff.*
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

class HubViewModel(
    player: Player,
    private val itemsDao: ItemsDao,
    private val monstersDao: MonstersDao,
) : ContainerHost<HubState, HubSideEffect>, ViewModel() {
    override val container: Container<HubState, HubSideEffect> =
        container(
            initialState = HubState(
                player = player,
                items = emptyList(),
                monster = null
            )
        )

    init {
        getUsersByPlayerId()
        getMonster(true)
    }

    private fun getUsersByPlayerId() = intent {
        val users = itemsDao.getAllItemsByPlayerId(state.player.id)
        reduce { state.copy(items = users) }
    }

    private fun getMonster(fromDB: Boolean) = intent {
        val loadedMonster = monstersDao.getMonsters().firstOrNull()
        var nextMonsterImage = monsterList.getRandom()
        while (nextMonsterImage == loadedMonster?.image) {
            Log.e("nextMonster skip iteration", nextMonsterImage.toString())
            nextMonsterImage = monsterList.getRandom()
        }
        val randomMonster = Monster(
            ownerId = state.player.id,
            name = "NoGeneratedMonsterName",
            image = nextMonsterImage,
            health = Durability(100, 100),
            lootMultiplier = 1
        )
        val nextMonster = loadedMonster ?: randomMonster
        reduce { state.copy(monster = if (fromDB) nextMonster else randomMonster) }
    }

    private fun deleteMonster() = intent {
        state.monster?.let {
            monstersDao.deleteMonster(it)
            reduce { state.copy(monster = null) }
        }
    }

    fun hitMonster(damage: Int) = intent {
        state.monster?.let {
            val healthAfterDamage = it.health.current - damage
            reduce {
                state.copy(
                    monster = it.copy(
                        health = it.health.copy(
                            current = if (healthAfterDamage>0) healthAfterDamage else 0
                        )
                    )
                )
            }
            if (healthAfterDamage<=0) {
                repeat(it.lootMultiplier) {
                    ItemClass.entries.getRandom().tryToGetIt()?.let { stats ->
                        Log.i("get Item", stats.compositeName)
                        insertItem(stats.toItem(state.player.id))
                    }
                }
                deleteMonster()
                delay(1000)
                getMonster(false)
            }
        }
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
