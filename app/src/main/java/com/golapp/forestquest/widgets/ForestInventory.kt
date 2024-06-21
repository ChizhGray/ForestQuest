package com.golapp.forestquest.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/*
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForestInventory(
    modifier: Modifier = Modifier,
    title: String,
    items: Map<String, IForestItem>,
    onItemUse: (result: ForestUseResult, item: IForestItem) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth()
        )
        FlowRow {
            items.entries.forEach { (_, item) ->
                ForestInventoryItem(
                    image = item.itemClass.image,
                    title = item.itemClass.title,
                    item = item
                ) { result, usedItem ->
                    onItemUse(result, usedItem)
                }
            }
        }
    }
}*/
