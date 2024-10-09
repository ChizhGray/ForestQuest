package com.golapp.forestquest.staff

inline fun <reified T> List<T>.getRandom(): T {
    val randomMath = Math.random()
    val randomInt = (randomMath * size).toInt()
    return this[randomInt]
}