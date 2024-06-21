package com.golapp.forestquest.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.forestrun.newgame.extentions.vibrateOnce

@Composable
fun ForestDropDownListButton(
    modifier: Modifier = Modifier,
    title: String,
    isActive: Boolean,
    items: List<String>,
    onItemClick: (index: Int) -> Unit
) {
    val context = LocalContext.current
    val menu = remember { mutableStateOf(false) }
    Box {
        ForestButton(
            modifier = modifier,
            title = title,
            isActive = isActive
        ) {
            menu.value = true
        }
        DropdownMenu(
            expanded = menu.value,
            onDismissRequest = { menu.value = false }
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    Text(
                        text = item,
                        Modifier
                            .padding(5.dp)
                            .clickable {
                                context.vibrateOnce(20)
                                menu.value = false
                                onItemClick(index)
                            }
                    )
                }
            }
        }
    }
}