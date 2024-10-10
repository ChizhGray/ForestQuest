package com.golapp.forestquest.screens.hub

import android.util.Log
import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.staff.*
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container
import java.util.UUID

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
        getItemsByPlayerId()
        getMonsterFromDB()
    }

    private fun getItemsByPlayerId() = intent {
        val itemsFromDB = itemsDao.getAllItemsByPlayerId(state.player.id)
        reduce { state.copy(items = itemsFromDB) }
    }

    private fun getRandomMonster() = intent {
        var nextMonsterImage = monsterList.getRandom()
        while (nextMonsterImage == state.monster?.image) {
            Log.e("getRandomMonster nextMonster skip iteration", nextMonsterImage.toString())
            nextMonsterImage = monsterList.getRandom()
        }
        val randomUUID = UUID.randomUUID().toString()
        val randomMonster = Monster(
            id = state.player.id,
            name = randomUUID.takeLast(8),
            image = nextMonsterImage,
            health = Durability(100, 100),
            lootMultiplier = 10
        )
        reduce { state.copy(monster = randomMonster) }
    }

    fun saveMonsterToDB() = intent {
        val monsterInDB = monstersDao.getMonstersBy(state.player.id).firstOrNull()
        if (monsterInDB==null) {
            state.monster?.let { insertMonster(it) }
        } else {
            state.monster?.let { updateMonsterFromDB(it) }
        }
    }

    private suspend fun updateMonsterFromDB(monster: Monster)  {
        monstersDao.updateMonster(monster)
    }

    private suspend fun insertMonster(monster: Monster)  {
        monstersDao.insertMonster(monster)
    }

    private fun getMonsterFromDB() = intent {
        val monsters = monstersDao.getMonstersBy(state.player.id)
        Log.e("monstersInDB", "${monsters.size} $monsters")
        val monster = monsters.firstOrNull()
        if (monster == null) getRandomMonster()
        else reduce { state.copy(monster = monster) }
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
                        Log.i("get Item (#$it)", stats.compositeName)
                        insertItem(stats.toItem(state.player.id))
                    } ?: Log.e("get Item (#$it)", "failed")
                }
                getRandomMonster()
            }
        }
    }

    fun removeAllItems() = intent {
        itemsDao.deleteItemsByPlayerId(state.player.id)
        reduce { state.copy(items = emptyList()) }
    }

    fun insertItem(item: Item) = intent {
        itemsDao.insertItem(item)
        getItemsByPlayerId()
    }

    fun deleteItem(item: Item) = intent {
        itemsDao.deleteItem(item)
        getItemsByPlayerId()
    }
}
