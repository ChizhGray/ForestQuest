package com.golapp.forestquest.user

import androidx.compose.foundation.clickable
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
        state.users.forEach { user ->
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${user.id}, ${user.name}, ${user.age}")
                IconButton(onClick = { vm.deleteUser(user) }) {
                    Text(text = "del")
                }
            }
        }


    }
}