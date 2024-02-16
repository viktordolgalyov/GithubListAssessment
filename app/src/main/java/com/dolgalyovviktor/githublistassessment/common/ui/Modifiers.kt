package com.dolgalyovviktor.githublistassessment.common.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.disableSplitMotionEvents(): Modifier = this.pointerInput(Unit) {
    awaitEachGesture {
        var touchActive = false

        while (true) {
            val event = awaitPointerEvent()
            val eventChanges = event.changes

            if (eventChanges.size > 1) {
                eventChanges.forEach { it.consume() }
            } else {
                eventChanges.forEach { pointerInputChange ->
                    if (!touchActive && pointerInputChange.pressed) {
                        touchActive = true
                        pointerInputChange.consume()
                    } else if (touchActive && !pointerInputChange.pressed) {
                        touchActive = false
                    }
                }
            }
        }
    }
}