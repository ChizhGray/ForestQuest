package com.golapp.forestquest.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.example.forestrun.newgame.extentions.vibrateOnce
import com.golapp.forestquest.widgets.*

/*@Composable
fun ForestInventoryItem(
    modifier: Modifier = Modifier,
    image: Int,
    title: String,
    item: IForestItem,
    onItemUse: (result: ForestUseResult, item: IForestItem) -> Unit
) {
    val context = LocalContext.current
    val info = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .size(50.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(3.dp))
            .clickable {
                info.value = true
                context.vibrateOnce(20)
            }
    ) {
        Image(painter = painterResource(id = image), contentDescription = null)
        Text(text = title, modifier = Modifier
            .align(Alignment.TopStart)
            .background(Color.LightGray.copy(alpha = .3f)), fontSize = 10.sp)
        if (item.count>1) Text(text = item.count.toString(), modifier = Modifier
            .align(Alignment.BottomEnd)
            .background(Color.LightGray.copy(alpha = .3f)), fontSize = 12.sp)
        DropdownMenu(
            expanded = info.value,
            onDismissRequest = { info.value = false },
            modifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(3.dp))
                .widthIn(max = 250.dp)
        ) {
            Column(Modifier.padding(horizontal = 3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ForestIcon(image = item.itemClass.image)
                    Text(text = item.itemClass.title)
                }
                Text(text = item.getInfo)
                if (item is IForestUsable) ForestButton(title = item.useText, isActive = true) {
                    context.vibrateOnce(20)
                    val useResult = item.use()
                    info.value = false
                    onItemUse(useResult, item)
                }
            }
        }
    }
}*/
