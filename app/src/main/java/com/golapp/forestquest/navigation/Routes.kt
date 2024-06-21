package com.golapp.forestquest.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class Routes(val route: String) {
    StartScreen("StartScreen"),
    HubScreen("HubScreen/{id}");
    inline fun <reified T> setArgument(value: T) =
        if (route.contains("{id}"))
            route.replace("{id}", Json.encodeToString(value))
        else route
}

inline fun <reified T> NavBackStackEntry.getArgument() =
    arguments?.getString("id")?.let { Json.decodeFromString<T>(it) }