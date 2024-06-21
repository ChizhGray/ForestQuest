package com.golapp.forestquest.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.golapp.forestquest.room.entities.Player

/*
@Composable
fun ForestPlayerStats(
    modifier: Modifier = Modifier,
    player: Player,
    playerData: ForestPlayerData
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
            .padding(3.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(modifier) {
            Column(
                Modifier.fillMaxWidth(.45f)
            ) {
                ForestPlayerRow(title = "имя", value = player.name)
                ForestPlayerRow(title = "класс", value = player.classOfPlayer.name)
                Spacer(modifier = Modifier.height(10.dp))
                ForestPlayerRow(title = "уровень", value = playerData.level.toString())
                ForestPlayerRow(title = "опыт", value = playerData.experience.toString())
                ForestPlayerRow(title = "сила", value = playerData.strength.toString())
                ForestPlayerRow(title = "интелект", value = playerData.intellect.toString())
                ForestPlayerRow(title = "ловкость", value = playerData.agility.toString())
                ForestPlayerRow(title = "выносливость", value = playerData.stamina.toString())
                ForestPlayerRow(title = "удача", value = playerData.luck.toString())
            }
            Column {
                Text(text = "Принятые задания:")
                playerData.quests.keys.forEach { id ->
                    ForestPlayerRow(title = "задание", value = id.take(8))
                }
            }
        }
    }
}*/
