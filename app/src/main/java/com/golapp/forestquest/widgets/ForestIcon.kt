package com.golapp.forestquest.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ForestIcon(image: Int, modifier: Modifier = Modifier) {
    Box(modifier.size(25.dp).clip(RoundedCornerShape(3.dp))) {
        Image(painter = painterResource(id = image), contentDescription = null)
    }
}