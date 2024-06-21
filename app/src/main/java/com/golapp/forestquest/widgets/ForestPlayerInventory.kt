package com.golapp.forestquest.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/*
@Composable
fun ForestPlayerInventory(
    modifier: Modifier = Modifier,
    inventory: Map<String, IForestItem>,
    onItemUse: (result: ForestUseResult, item: IForestItem) -> Unit
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        ForestInventory(
            title = "Инвентарь",
            items = inventory.filter { it.value.count > 0 }
        ) { result, item ->
            onItemUse(result, item)
        }
    }
}*/
