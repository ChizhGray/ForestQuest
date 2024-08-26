package com.golapp.forestquest.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.golapp.forestquest.room.entities.Item
import com.golapp.forestquest.widgets.ForestTopBar

@Composable
fun HubScreen(
    vm: HubViewModel,
    onBackClick: () -> Unit
) {
    val state by vm.container.stateFlow.collectAsState()

    Column {
        ForestTopBar(title = "HubScreen") {
            onBackClick()
        }
        Box {
            Text(text = "userId=${state.player.id}", Modifier.padding(3.dp).clip(RoundedCornerShape(5.dp)).background(Color.LightGray).fillMaxWidth())
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(
                onClick = {
                    vm.insertUser(
                        Item(
                            name = System.currentTimeMillis().toString().takeLast(8),
                            age = (Math.random()*100).toInt(),
                            playerId = state.player.id
                        )
                    )
                }
            ) {
                Text(text = "set user")
            }
            Button(onClick = { vm.getUsers() }) {
                Text(text = "getUsers")
            }
        }
        if (state.items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No users")
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.8f)) {
                Text(text = "id", Modifier.fillMaxWidth(.1f))
                Text(text = "player", Modifier.fillMaxWidth(.2f))
                Text(text = "name", Modifier.fillMaxWidth(.4f))
                Text(text = "age", Modifier.fillMaxWidth())
            }
            LazyColumn {
                items(state.items) { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(1.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.LightGray)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.8f)) {
                            Text(text = "${user.id}", Modifier.fillMaxWidth(.1f))
                            Text(text = user.playerId.toString(), Modifier.fillMaxWidth(.2f))
                            Text(text = user.name, Modifier.fillMaxWidth(.4f))
                            Text(text = "${user.age}", Modifier.fillMaxWidth())
                        }
                        Row(Modifier.fillMaxWidth()) {
                            IconButton(onClick = { vm.deleteUser(user) }) {
                                Text(text = "del")
                            }
                        }
                    }
                }
            }
        }
    }
}