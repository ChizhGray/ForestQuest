package com.golapp.forestquest

import android.app.Application
import com.golapp.forestquest.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class ForestApplication : Application() {
    private lateinit var koinApplication: KoinApplication
    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            androidContext(this@ForestApplication)
            modules(appModule)
        }
    }
}