package org.artisancode.mywebkotlin.utils

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp

enum class ScreenSize {
    MOBILE,    // < 768px
    TABLET,    // 768px - 1024px
    DESKTOP    // > 1024px
}

@Composable
fun rememberScreenSize(): ScreenSize {
    var screenSize by remember { mutableStateOf(ScreenSize.DESKTOP) }
    var windowWidth by remember { mutableStateOf(1920) }

    LaunchedEffect(Unit) {
        // Detectar tamaño inicial
        windowWidth = kotlinx.browser.window.innerWidth
        screenSize = when {
            windowWidth < 768 -> ScreenSize.MOBILE
            windowWidth < 1024 -> ScreenSize.TABLET
            else -> ScreenSize.DESKTOP
        }

        // Listener para cambios de tamaño
        val resizeListener: (dynamic) -> Unit = {
            windowWidth = kotlinx.browser.window.innerWidth
            screenSize = when {
                windowWidth < 768 -> ScreenSize.MOBILE
                windowWidth < 1024 -> ScreenSize.TABLET
                else -> ScreenSize.DESKTOP
            }
        }

        kotlinx.browser.window.addEventListener("resize", resizeListener)
    }

    return screenSize
}

// Helper para obtener valores responsive
@Composable
fun responsiveValue(
    mobile: Dp,
    tablet: Dp = mobile,
    desktop: Dp
): Dp {
    return when (rememberScreenSize()) {
        ScreenSize.MOBILE -> mobile
        ScreenSize.TABLET -> tablet
        ScreenSize.DESKTOP -> desktop
    }
}