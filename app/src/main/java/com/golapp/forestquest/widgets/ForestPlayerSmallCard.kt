package com.golapp.forestquest.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.golapp.forestquest.room.entities.Player

@Composable
fun ForestPlayerSmallCard(
    modifier: Modifier = Modifier,
    player: Player,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier
            .border(1.dp, if (selected) Color.Green else Color.Black, RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(5.dp)) {
            Box(Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))) {
                Image(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "player_image"
                )
            }
            ForestPlayerRow(title = "name", value = player.name)
            ForestPlayerRow(title = "class", value = player.classOfPlayer)
        }
    }
}