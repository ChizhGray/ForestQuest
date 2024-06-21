package com.golapp.forestquest.utils

import android.content.Context
import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private fun log(text: String?) {
    Log.i("Store", "$text")
}

open class Store(context: Context, private val storeName: String) {
    object Names {
        const val STORE_NAME = "StoreName"
        const val PREF_NAME_1 = "PrefName1"
        const val PREF_NAME_2 = "PrefName2"
        const val PREF_NAME_3 = "PrefName3"
    }

    private val store = context.getSharedPreferences(
        storeName,
        Context.MODE_PRIVATE
    )

    fun clearAll() {
        log("clearing store (${store.all.size})")
        store.edit().apply { clear(); apply() }
        log("store cleared (${store.all.size})")
    }

    fun showAll() {
        log("~~~~~~~~~~~~~~~~~~~~~~[STORE: $storeName]~~~~~~~~~~~~~~~~~~~~~~~~")
        store.all.entries.forEach { log("${it.key} = ${it.value}") }
        log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }

    fun getRaw() = store.all

    fun setStringValue(key: String, value: String?) {
        log("set($key) = $value")
        store.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getStringValue(key: String, defaultValue: String? = null): String? {
        val result = store.getString(key, defaultValue)
        log("get($key) = $result")
        return result
    }

    inline fun <reified T> save(key: String, value: T) {
        setStringValue(key, Json.encodeToString(value))
    }

    inline fun <reified T> load(key: String): T? {
        getStringValue(key)?.let { return Json.decodeFromString<T>(it) } ?: return null
    }

    inline fun <reified T> getAll(): Map<String, T> {
        val result = mutableMapOf<String, T>()
        getRaw().forEach { rawItem ->
            load<T>(rawItem.key)?.let { result[rawItem.key] = it }
        }
        return result
    }
}