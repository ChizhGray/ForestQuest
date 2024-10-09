package com.golapp.forestquest.screens.hub

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.golapp.forestquest.room.entities.Item
import com.golapp.forestquest.staff.*
import com.golapp.forestquest.widgets.ForestTopBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HubScreen(
    vm: HubViewModel,
    onBackClick: () -> Unit
) {
    val state by vm.container.stateFlow.collectAsState()
    val isTable = remember { mutableStateOf(false) }
    Column {
        ForestTopBar(
            title = "HubScreen",
            extraButton = true,
            extraButtonText = if (isTable.value) "inv" else "tbl",
            onExtraClick = { isTable.value = !isTable.value }
        ) {
            onBackClick()
        }
        Box {
            Text(text = "playerId=${state.player.id}",
                Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.LightGray)
                    .fillMaxWidth())
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(
                onClick = {
                    ItemClass.entries.getRandom().let { randomItem ->
                        randomItem.tryToGetIt()?.let {
                            val stats = it.getItemStats()
                            Log.i("get Item", stats.compositeName)
                            vm.insertItem(stats.toItem(state.player.id))
                        }
                    }
                }
            ) {
                Text(text = "get Item(${state.items.size})")
            }
            Button(onClick = { vm.getItems() }) {
                Text(text = "get All Players Items")
            }
        }
        if (state.items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Items")
            }
        } else {
            if (isTable.value) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.8f)) {
                    Text(text = "id", Modifier.fillMaxWidth(.1f))
                    Text(text = "ownerId", Modifier.fillMaxWidth(.2f))
                    Text(text = "name", Modifier.fillMaxWidth(.4f))
                    Text(text = "itemType", Modifier.fillMaxWidth())
                }
                LazyColumn {
                    items(state.items) { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(1.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.8f)) {
                                Text(text = "${item.id}", Modifier.fillMaxWidth(.1f))
                                Text(text = item.ownerId.toString(), Modifier.fillMaxWidth(.2f))
                                Text(text = item.name, Modifier.fillMaxWidth(.4f))
                                Text(text = item.itemType, Modifier.fillMaxWidth())
                            }
                            Row(Modifier.fillMaxWidth()) {
                                IconButton(onClick = { vm.deleteItem(item) }) {
                                    Text(text = "del")
                                }
                            }
                        }
                    }
                }
            } else {
                FlowRow {
                    state.items.sortedBy { it.name }.groupBy { it.name }.forEach { itemMap ->
                        val menu = remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .clickable {
                                    menu.value = true
                                },
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            itemMap.value.firstOrNull()?.let {
                                Image(
                                    painter = painterResource(id = it.icon),
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = null
                                )
                                Text(
                                    text = itemMap.value.size.toString(),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(3.dp)
                                )
                                DropdownMenu(
                                    modifier = Modifier.widthIn(min = 60.dp),
                                    expanded = menu.value,
                                    onDismissRequest = { menu.value = false }
                                ) {
                                    Text(text = itemMap.key)
                                    Text(text = it.itemType)
                                    Text(
                                        text = "Use",
                                        color = Color.Green,
                                        modifier = Modifier.clickable {
                                            vm.deleteItem(it)
                                            menu.value = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}