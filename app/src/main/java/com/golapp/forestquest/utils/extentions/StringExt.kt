package com.golapp.forestquest.utils.extentions

import com.golapp.forestquest.staff.PlayerClass

fun String.isOnlyLetters() =
    all { it.isLetter() }

fun String.getPlayerClass() =
    PlayerClass.entries.firstOrNull { it.eng == this }