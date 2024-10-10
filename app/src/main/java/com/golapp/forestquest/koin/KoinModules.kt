package com.golapp.forestquest.koin

import com.golapp.forestquest.AppDatabase
import com.golapp.forestquest.room.entities.Player
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.screens.hub.HubViewModel
import com.golapp.forestquest.screens.start.StartViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { getDatabase(get()) }
    single<ItemsDao> { get<AppDatabase>().itemsDao() }
    single<PlayerDao> { get<AppDatabase>().playerDao() }
    single<MonstersDao> { get<AppDatabase>().monstersDao() }
    viewModel {
        StartViewModel(
            playerDao = get(),
            itemsDao = get()
        )
    }
    viewModel {(player: Player) ->
        val context = androidApplication()
        HubViewModel(
            player = player,
            itemsDao = get(),
            monstersDao = get(),
            playerDao = get(),
            context = { context }
        )
    }
}