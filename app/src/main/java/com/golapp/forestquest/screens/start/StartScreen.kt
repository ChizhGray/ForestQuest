package com.golapp.forestquest.screens.start

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.forestrun.newgame.extentions.*
import com.golapp.forestquest.room.entities.Player
import com.golapp.forestquest.staff.PlayerClass
import com.golapp.forestquest.widgets.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
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
    val isBSVisible = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            Log.e("bs-test", it.name + " : " + isBSVisible.value)
            true
        }
    )
    fun SheetState.showBS() {
        isBSVisible.value = true
        uiScope.launch { expand() }
    }
    fun SheetState.hideBS() {
        isBSVisible.value = false
        uiScope.launch { hide() }
    }
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
    LaunchedEffect(key1 = Unit) {
        vm.getPlayers()
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
                                    sheetState.showBS()
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
                                ForestPlayerRow(title = "", value = "")
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
                            vm.selectPlayer(null)
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
    if (isBSVisible.value) ModalBottomSheet(
        sheetState = sheetState,
        dragHandle = {},
        shape = BottomSheetDefaults.HiddenShape,
        onDismissRequest = {
            sheetState.hideBS()
        }
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
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
                        colors = TextFieldDefaults.colors(
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
                        value = state.inputFieldValue,
                        onValueChange = {
                            vm.changeInputFieldValue(it)
                        }
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(text = "выбери класс:")
                        ForestDropDownListButton(
                            modifier = Modifier.fillMaxWidth(),
                            title = state.playerClass?.name ?: "No class",
                            isActive = true,
                            items = PlayerClass.entries.map { it.name },
                            onItemClick = {
                                vm.changePlayerClass(PlayerClass.entries[it])
                            }
                        )
                    }
                }
                ForestButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Создать игрока",
                    isActive = state.inputFieldValue.isNotBlank() && state.playerClass != null
                ) {
                    state.playerClass?.let {
                        val newPlayer = Player(name = state.inputFieldValue, classOfPlayer = it.name, attack = 5, defence = 0)
                        vm.insertPlayer(newPlayer)
                        vm.selectPlayer(newPlayer)
                    }
                    sheetState.hideBS()
                }
            }
        }
    }
}