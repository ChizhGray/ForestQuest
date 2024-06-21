package com.golapp.forestquest.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ForestTopBar(
    title: String,
    modifier: Modifier = Modifier,
    backButton: Boolean = true,
    extraButton: Boolean = false,
    extraButtonText: String = "extra",
    onExtraClick: () -> Unit = {},
    onBackClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .heightIn(44.dp)
            .border(1.dp, Color.Black)
    ) {
        if (backButton) IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Image(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
        }
        Text(text = title, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
        if (extraButton) IconButton(onClick = onExtraClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = extraButtonText, color = Color.Red.copy(red = .6f))
        }
    }
}