package com.golapp.forestquest.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ForestConfirmButton(buttonTitle: String, confirmTitle: String, isActive: Boolean, onClick: () -> Unit) {
    val deleteMenu = remember {
        mutableStateOf(false)
    }
    Box(contentAlignment = Alignment.Center) {
        ForestButton(
            modifier = Modifier.fillMaxWidth(),
            title = buttonTitle,
            indicationColor = Color.Red,
            isActive = isActive
        ) {
            deleteMenu.value = true
        }
        DropdownMenu(
            expanded = deleteMenu.value,
            onDismissRequest = { deleteMenu.value = false }
        ) {
            Row {
                Text(
                    text = confirmTitle,
                    Modifier
                        .padding(5.dp)
                        .clickable {
                            if (isActive) {
                                deleteMenu.value = false
                                onClick()
                            }
                        }
                )
            }
        }
    }
}