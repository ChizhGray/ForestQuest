package com.golapp.forestquest.screens.hub

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forestrun.newgame.extentions.showToast
import com.example.forestrun.newgame.extentions.vibrateOnce
import com.golapp.forestquest.room.entities.*
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.staff.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container
import java.util.UUID

class HubViewModel(
    player: Player,
    private val playerDao: PlayerDao,
    private val itemsDao: ItemsDao,
    private val monstersDao: MonstersDao,
    private val context: () -> Application,
) : ContainerHost<HubState, HubSideEffect>, ViewModel() {
    override val container: Container<HubState, HubSideEffect> =
        container(
            initialState = HubState(
                player = player,
                items = emptyList(),
                monster = null
            )
        )

    private var job: Job? = null

    init {
        getItemsByPlayerId()
        getMonsterFromDB()
    }

    fun ticker(action: () -> Unit) {
        if (job!=null) {
            job?.cancel()
            job = null
        }
        else job = viewModelScope.launch {
            while (true) {
                action()
                delay(500)
            }
        }
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
        context().vibrateOnce(20)
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

    fun useItem(item: Item) = intent {
        context().vibrateOnce(70)
        Log.i("useItem", item.itemType)
        when (item.itemType) {
            Basic.BasicWeapon.name -> {
                val newAttackStat = state.player.attack + 1
                viewModelScope.launch(Dispatchers.Main) {
                    context().showToast("player attack +1")
                }
                playerDao.updatePlayer(state.player.copy(attack = newAttackStat))
                reduce {
                    state.copy(player = state.player.copy(attack = newAttackStat))
                }
                deleteItem(item)
            }
        }
    }
}


































