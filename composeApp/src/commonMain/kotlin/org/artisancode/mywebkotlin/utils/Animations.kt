package org.artisancode.mywebkotlin.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

enum class RevealDirection {
    TOP, BOTTOM, LEFT, RIGHT
}

@Composable
fun ScrollReveal(
    trigger: Boolean = true,
    direction: RevealDirection = RevealDirection.BOTTOM,
    distance: Int = 80,
    duration: Int = 800,  // Reducido de 1000ms a 800ms
    delayMillis: Long = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(trigger) {
        if (trigger && !isVisible) {
            delay(delayMillis)
            isVisible = true
        }
    }

    // Easing personalizado más suave
    val customEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f)

    val enter = when (direction) {
        RevealDirection.TOP -> slideInVertically(
            initialOffsetY = { -distance },
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        )

        RevealDirection.BOTTOM -> slideInVertically(
            initialOffsetY = { distance },
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        )

        RevealDirection.LEFT -> slideInHorizontally(
            initialOffsetX = { -distance },
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        )

        RevealDirection.RIGHT -> slideInHorizontally(
            initialOffsetX = { distance },
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = duration,
                easing = customEasing
            )
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = enter,
        modifier = modifier
    ) {
        content()
    }
}