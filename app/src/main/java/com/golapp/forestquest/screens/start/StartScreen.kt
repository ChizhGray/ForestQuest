package com.golapp.forestquest.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import com.example.forestrun.newgame.extentions.*
import com.golapp.forestquest.room.entities.Player
import com.golapp.forestquest.widgets.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun StartScreen(
    vm: StartViewModel,
    onPlayClick: (player: Player) -> Unit
) {
    val state by vm.container.stateFlow.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val uiScope = rememberCoroutineScope()
    vm.collectSideEffect {
        when (it) {
            StartSideEffect.SimilarName -> {
                context.showToast("Такое имя уже есть")
            }
            StartSideEffect.PlayerCreated -> {
                focusManager.clearFocus()
                state.players.let { players ->
                    if (players.isNotEmpty()) scrollState.animateScrollToItem(players.lastIndex)
                }
            }
        }
    }
    Box {
        Column(
            Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Column(modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
                .padding(3.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(text = "выбери игрока или создай нового:")
                LazyRow(state = scrollState, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    items(state.players) { player ->
                        ForestPlayerSmallCard(
                            player = player,
                            selected = state.selectedPlayer?.id == player.id
                        ) {
                            context.vibrateOnce(20)
                            vm.selectPlayer(player)
                        }
                    }
                    item {
                        Box(
                            Modifier
                                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                                .clickable {
                                    uiScope.launch {
                                        vm.insertPlayer(
                                            Player(
                                                name = "test_name",
                                                classOfPlayer = "test_class"
                                            )
                                        )
                                    }
                                }
                        ) {
                            Column(Modifier.padding(5.dp)) {
                                Box(
                                    Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))) {
                                    Image(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "player_image"
                                    )
                                }
                                ForestPlayerRow(title = "", value = "Создать")
                                ForestPlayerRow(title = "", value = "персонажа")
                            }
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    ForestButton(
                        modifier = Modifier.fillMaxWidth(.5f),
                        title = "Играть",
                        isActive = state.selectedPlayer != null
                    ) {
                        state.selectedPlayer?.let { playerWithData ->
                            onPlayClick(playerWithData)
                        }
                    }
                    ForestConfirmButton(
                        buttonTitle = state.selectedPlayer?.let { player ->
                            "Удалить ${player.name}"
                        } ?: "Удалить",
                        confirmTitle = "Да, хочу удалить",
                        isActive = state.selectedPlayer != null
                    ) {
                        state.selectedPlayer?.let { player ->
                            context.vibrateOnce(50)
                            vm.deletePlayer(player)
                        }
                    }
                }
            }
        }
    }
    /*val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetScaffoldState(
            initialValue = BottomSheetValue.Collapsed,
            density = LocalDensity.current,
            confirmValueChange = {
                when(it) {
                    BottomSheetValue.Collapsed -> focusManager.clearFocus()
                    BottomSheetValue.Expanded -> { Log.i("BottomSheet", it.name) }
                }
                true
            }
        )
    )
    BottomSheetScaffold(
        modifier = Modifier.imePadding(),
        sheetPeekHeight = 0.dp,
        scaffoldState = sheetState,
        topBar = {
            ForestWidgetTopBar("Начальный экран (${state.value.players.size} персонажей)")
        },
        content = {

        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
                        .padding(3.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.5f),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Black,
                                cursorColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            placeholder = { Text(text = "введите имя") },
                            value = state.value.inputFieldValue,
                            onValueChange = {
                                vm.changeInputFieldValue(it)
                            }
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(text = "выбери класс:")
                            ForestDropDownListButton(
                                modifier = Modifier.fillMaxWidth(),
                                title = state.value.playerClass.title,
                                isActive = true,
                                items = ForestPlayerClass.entries.map { it.title },
                                onItemClick = {
                                    vm.changePlayerClass(ForestPlayerClass.entries[it])
                                }
                            )
                        }
                    }
                    ForestButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Создать игрока",
                        isActive = state.value.inputFieldValue.isNotBlank()
                    ) {
                        vm.addNewPlayer()
                        uiScope.launch { sheetState.bottomSheetState.collapse() }
                    }
                }
            }
        }
    )
*/

}