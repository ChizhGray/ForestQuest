package com.golapp.forestquest.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.forestrun.newgame.extentions.vibrateOnce

@Composable
fun ForestButton(
    modifier: Modifier = Modifier,
    title: String,
    isActive: Boolean,
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp),
    backgroundColor: Color = Color.LightGray,
    indicationColor: Color = Color.Green.copy(green = .6f),
    vibrateDuration: Long = 20,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .heightIn(40.dp).border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(if (isActive) backgroundColor else backgroundColor.copy(alpha = .8f))
            .clickable { if (isActive) { onClick(); context.vibrateOnce(vibrateDuration) } },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, Modifier.padding(paddingValues), color = if (isActive) indicationColor else Color.Black.copy(alpha = .2f))
    }
}