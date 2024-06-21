package com.golapp.forestquest.staff

import kotlinx.serialization.Serializable

@Serializable
enum class PlayerClass(val rus: String, val eng: String) {
    Warrior("Воин", "warrior"),
    Mage("Маг", "mage")
}