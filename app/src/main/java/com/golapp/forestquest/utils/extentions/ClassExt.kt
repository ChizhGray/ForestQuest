package com.example.forestrun.newgame.extentions

import android.util.Log

inline fun <reified T> T.logDeclaredFields(): Map<String, Any?> {
    val result: MutableMap<String, Any?> = mutableMapOf()
    T::class.java.declaredFields.forEach {
        it.isAccessible = true
        val name = it.name
        val value = it.get(this)
        result[name] = value
        Log.i("declaredFields", "$name = $value")
    }
    return result
}