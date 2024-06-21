package com.golapp.forestquest.koin

import com.golapp.forestquest.AppDatabase
import com.golapp.forestquest.room.interfaces.*
import com.golapp.forestquest.screens.hub.HubViewModel
import com.golapp.forestquest.screens.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { getDatabase(get()) }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<PlayerDao> { get<AppDatabase>().playerDao() }
    viewModel { StartViewModel(playerDao = get()) }
    viewModel { HubViewModel(userDao = get()) }
}