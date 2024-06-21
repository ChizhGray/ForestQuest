package com.example.forestrun.newgame.extentions

import android.content.Context
import android.os.*
import android.widget.Toast
import androidx.core.content.getSystemService

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, text, duration)
    toast.show()
}

/**
 * #### Elements of buzzing and non-buzzing takes ->
 * #### wait 0 ms, buzz 200ms, wait 100ms, buzz 300ms
 * #### [vibrateWave] ( [longArrayOf] (0, 200, 100, 300) )
 * */
fun Context.vibrateWave(pattern: LongArray) {
    val buzzer = getSystemService<Vibrator>()
    buzzer?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buzzer.vibrate(VibrationEffect.createWaveform(pattern,-1))
        } else {
            //deprecated in API 26
            buzzer.vibrate(pattern, -1)
        }
    }
}

fun Context.vibrateOnce(duration: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
    val buzzer = getSystemService<Vibrator>()
    buzzer?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buzzer.vibrate(VibrationEffect.createOneShot(duration, amplitude))
        } else {
            //deprecated in API 26
            buzzer.vibrate(longArrayOf(0, duration), -1)
        }
    }
}