package org.artisancode.mywebkotlin.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.artisancode.mywebkotlin.models.NavigationItems
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.rememberScreenSize

@Composable
fun Header(
    currentSection: String = "home",
    onNavigate: (String) -> Unit = {}
) {
    val screenSize = rememberScreenSize()
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val handleScroll: (dynamic) -> Unit = {
            if (isMenuOpen) {
                isMenuOpen = false
            }
        }
        kotlinx.browser.window.addEventListener("scroll", handleScroll)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = when (screenSize) {
                        ScreenSize.MOBILE -> 24.dp
                        ScreenSize.TABLET -> 32.dp
                        ScreenSize.DESKTOP -> 48.dp
                    },
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            Text(
                text = "About Me",
                style = when (screenSize) {
                    ScreenSize.MOBILE -> MaterialTheme.typography.headlineSmall
                    else -> MaterialTheme.typography.headlineMedium
                },
                color = MaterialTheme.colorScheme.onSurface
            )

            // Desktop Navigation
            if (screenSize == ScreenSize.DESKTOP) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    NavigationItems.items.forEach { item ->
                        NavLink(
                            item = item,
                            isActive = currentSection == item.targetSection,
                            onClick = { onNavigate(item.targetSection) }
                        )
                    }
                }
            }

            // Mobile Menu Icon
            if (screenSize != ScreenSize.DESKTOP) {
                IconButton(onClick = { isMenuOpen = !isMenuOpen }) {
                    Icon(
                        imageVector = if (isMenuOpen) Icons.Default.Close else Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // Mobile Menu Dropdown
        if (isMenuOpen && screenSize != ScreenSize.DESKTOP) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NavigationItems.items.forEach { item ->
                    TextButton(
                        onClick = {
                            onNavigate(item.targetSection)
                            isMenuOpen = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (currentSection == item.targetSection)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavLink(
    item: org.artisancode.mywebkotlin.models.NavItem,
    isActive: Boolean,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    val linkColor by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.primary
            isHovered -> MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            else -> MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(200, easing = FastOutSlowInEasing)
    )

    TextButton(
        onClick = onClick,
        modifier = Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    when (event.type) {
                        PointerEventType.Enter -> isHovered = true
                        PointerEventType.Exit -> isHovered = false
                    }
                }
            }
        }
    ) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyLarge,
            color = linkColor
        )
    }
}