package com.golapp.forestquest.screens.hub

import android.app.Application
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
                monster = null,
                autoBattleIsEnable = false
            )
        )

    private var autoBattleTickerJob: Job? = null
    private var autoBattleIconAnimateJob: Job? = null

    init {
        getItemsByPlayerId()
        getMonsterFromDB()
    }

    fun animate(delay: Long, onEnable: () -> Unit, onDisable: () -> Unit, action: () -> Unit) {
        if (autoBattleIconAnimateJob!=null) {
            autoBattleIconAnimateJob?.cancel()
            autoBattleIconAnimateJob = null
            onDisable()
        }
        else autoBattleIconAnimateJob = viewModelScope.launch {
            onEnable()
            while (true) {
                action()
                delay(delay)
            }
        }
    }

    fun autoBattle(delay: Long, action: () -> Unit) = intent {
        if (autoBattleTickerJob!=null) {
            autoBattleTickerJob?.cancel()
            autoBattleTickerJob = null
            switchAutoBattleFlag(false)
        } else autoBattleTickerJob = viewModelScope.launch {
            switchAutoBattleFlag(true)
            while (true) {
                action()
                delay(delay)
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

    fun switchAutoBattleFlag(flag: Boolean) = intent {
        reduce {
            state.copy(
                autoBattleIsEnable = flag
            )
        }
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
            Basic.BasicWeapon.name,
            WeaponElement.Cold.name,
            WeaponElement.Flame.name -> {
                val newAttackStat = when(item.itemType) {
                    Basic.BasicWeapon.name -> 1
                    WeaponElement.Cold.name -> WeaponElement.Cold.attack
                    WeaponElement.Flame.name -> WeaponElement.Flame.attack
                    else -> 0
                }
                viewModelScope.launch(Dispatchers.Main) {
                    context().showToast("player attack +$newAttackStat")
                }
                playerDao.updatePlayer(state.player.copy(attack = state.player.attack + newAttackStat))
                reduce {
                    state.copy(player = state.player.copy(attack = state.player.attack + newAttackStat))
                }
                deleteItem(item)
            }
        }
    }
}


































