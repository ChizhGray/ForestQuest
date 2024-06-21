package com.golapp.forestquest.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ForestPlayerRow(title: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(text = title, color = Color.Gray)
        Text(text = value)
    }
}
