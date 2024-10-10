package com.golapp.forestquest.widgets.bounce

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource

@Composable
fun BounceImage(modifier: Modifier = Modifier, image: Int, onClick: () -> Unit) {
    var currentState: BounceState by remember { mutableStateOf(BounceState.Released) }
    val transition = updateTransition(targetState = currentState, label = "animation")

    val scale: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = 900f) }, label = ""
    ) { state ->
        // When the item is pressed , reduce
        // its size by 5% or make its size 0.95
        // of its original size
        // Change this value to see effect
        if (state == BounceState.Pressed) {
            0.55f
        } else {
            // When the item is released ,
            // make it of its original size
            1f
        }
    }
    // Basic compose Box Layout
    Box(
        Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        currentState = BounceState.Pressed // Set the currentState to Pressed  to trigger Pressed animation
                        tryAwaitRelease() // Waits for the tap to release before returning the call
                        currentState = BounceState.Released // Set the currentState to Release to trigger Release animation
                        onClick()
                    }
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "bounceImage",
            modifier = modifier
        )
    }
}