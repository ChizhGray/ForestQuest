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
import com.golapp.forestquest.utils.extentions.clickableWithoutIndication
import com.golapp.forestquest.widgets.ForestTopBar
import com.golapp.forestquest.widgets.bounce.BounceImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HubScreen(
    vm: HubViewModel,
    onBackClick: () -> Unit
) {
    val state by vm.container.stateFlow.collectAsState()
    val isTable = remember { mutableStateOf(false) }

    DisposableEffect(key1 = Unit) {
        onDispose {
            vm.saveMonsterToDB()
        }
    }

    Column {
        ForestTopBar(
            title = "HubScreen",
            extraButton = true,
            extraButtonText = if (isTable.value) "inv" else "tbl",
            onExtraClick = { isTable.value = !isTable.value }
        ) {
            onBackClick()
        }
        Column(
            Modifier
                .fillMaxWidth()
                .height(if (isTable.value) 0.dp else 200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isTable.value) state.monster?.let {
                Box(modifier = Modifier
                    .padding(top = 10.dp)
                    .border(1.dp, Color.Black)
                    .size(100.dp, 20.dp)
                ) {
                    Box(
                        Modifier
                            .background(Color.Green)
                            .fillMaxHeight()
                            .fillMaxWidth(it.health.current.toFloat() / it.health.max)
                            .align(Alignment.CenterStart)
                    )
                    Text(text = "${it.health.current}/${it.health.max}", modifier = Modifier.align(Alignment.Center), fontSize = 12.sp)
                }
                Text(text = it.name)
                BounceImage(image = it.image, modifier = Modifier
                    .padding(top = 5.dp)
                    .size(250.dp)
                ) {
                    vm.hitMonster(state.player.attack)
                }
            }
        }
        if (isTable.value) Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(
                onClick = {
                    ItemClass.entries.getRandom().tryToGetIt()?.let { stats ->
                        Log.i("get Item", stats.compositeName)
                        vm.insertItem(stats.toItem(state.player.id))
                    }
                }
            ) {
                Text(text = "get Item")
            }
            Button(onClick = { vm.removeAllItems() }) {
                Text(text = "remove all items")
            }
        }
        if (state.items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Items(${state.items.size})")
            }
        } else {
            if (isTable.value) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxWidth(.8f)) {
                    Text(text = "id", Modifier.fillMaxWidth(.1f))
                    Text(text = "ownerId", Modifier.fillMaxWidth(.3f))
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
                                Text(text = "${item.id}", Modifier.fillMaxWidth(.1f), fontSize = 10.sp)
                                Text(text = item.ownerId.toString(), Modifier.fillMaxWidth(.3f), fontSize = 10.sp)
                                Text(text = item.name, Modifier.fillMaxWidth(.4f), fontSize = 10.sp)
                                Text(text = item.itemType, Modifier.fillMaxWidth(), fontSize = 10.sp)
                            }
                            Text(text = "delete", modifier = Modifier.clickable { vm.deleteItem(item) })
                        }
                    }
                }
            } else {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier
                        .padding(2.dp, 5.dp)
                        .border(1.dp, Color.Black)
                        .padding(5.dp, 10.dp)) {
                        Text(text = "player attack:")
                        Text(text = state.player.attack.toString(), color = Color.Red)
                    }
                    Text(text = "Auto", modifier = Modifier.padding(horizontal = 10.dp).clickable { vm.ticker { vm.hitMonster(state.player.attack) } })
                }

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
                                            vm.useItem(it)
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