package com.golapp.forestquest.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.golapp.forestquest.room.entities.User

@Composable
fun UserScreen(
    paddingValues: PaddingValues,
    vm: UserViewModel
) {
    val state by vm.container.stateFlow.collectAsState()

    Column(Modifier.padding(paddingValues)) {

        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(
                onClick = {
                    vm.insertUser(User(name = System.currentTimeMillis().toString().takeLast(8), age = (Math.random()*100).toInt()))
                }
            ) {
                Text(text = "set user")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.7f)) {
            Text(text = "id", Modifier.fillMaxWidth(.2f))
            Text(text = "name", Modifier.fillMaxWidth(.6f))
            Text(text = "age", Modifier.fillMaxWidth())
        }
        state.users.forEach { user ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.7f)) {
                    Text(text = "${user.id}", Modifier.fillMaxWidth(.2f))
                    Text(text = user.name, Modifier.fillMaxWidth(.6f))
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