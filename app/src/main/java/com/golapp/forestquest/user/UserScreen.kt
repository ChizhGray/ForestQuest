package com.golapp.forestquest.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                    vm.insertUser(User(name = "Name", age = 25))
                }
            ) {
                Text(text = "set user")
            }
        }
        state.users.forEach { user ->
            Text(text = "${user.id}, ${user.name}, ${user.age}", modifier = Modifier.clickable {
                vm.deleteUser(user)
            })
        }


    }
}