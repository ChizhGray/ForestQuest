package com.golapp.forestquest.koin

import com.golapp.forestquest.AppDatabase
import com.golapp.forestquest.room.interfaces.UserDao
import com.golapp.forestquest.user.UserViewModel
import org.koin.android.ext.koin.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { getDatabase(get()) }
    single<UserDao> { provideDao(get()) }
    viewModel { UserViewModel(userDao = get()) }
}