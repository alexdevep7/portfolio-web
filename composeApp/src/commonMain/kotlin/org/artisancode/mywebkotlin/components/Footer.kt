package org.artisancode.mywebkotlin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.rememberScreenSize

@Composable
fun Footer(
    modifier: Modifier = Modifier
) {
    val screenSize = rememberScreenSize()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = when (screenSize) {
                    ScreenSize.MOBILE -> 24.dp
                    ScreenSize.TABLET -> 40.dp
                    else -> 80.dp
                },
                vertical = 40.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (screenSize == ScreenSize.DESKTOP) {
            // Desktop: Layout horizontal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FooterInfo()
                FooterSocial()
            }
        } else {
            // Mobile/Tablet: Layout vertical centrado
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FooterInfo()
                FooterSocial()
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        // Copyright
        if (screenSize == ScreenSize.DESKTOP) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CopyrightText()
                MadeWithText()
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MadeWithText()
                CopyrightText()
            }
        }
    }
}

@Composable
private fun FooterInfo() {
    val screenSize = rememberScreenSize()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = if (screenSize != ScreenSize.DESKTOP)
            Alignment.CenterHorizontally
        else
            Alignment.Start
    ) {
        Text(
            text = "Alfredo Castillo",
            style = when (screenSize) {
                ScreenSize.MOBILE -> MaterialTheme.typography.titleLarge
                else -> MaterialTheme.typography.headlineSmall
            },
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "alexbassist2013@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun FooterSocial() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SocialButton(
            icon = Icons.Default.Camera,
            contentDescription = "Instagram",
            tooltip = "Sígueme en Instagram",
            onClick = {
                kotlinx.browser.window.open(
                    "https://www.instagram.com/artisancode7/",
                    "_blank"
                )
            }
        )
        SocialButton(
            icon = Icons.Default.Business,
            contentDescription = "LinkedIn",
            tooltip = "Conéctate en LinkedIn",
            onClick = {
                kotlinx.browser.window.open(
                    "https://www.linkedin.com/in/alfredo-castillo-408166117",
                    "_blank"
                )
            }
        )
    }
}

@Composable
private fun CopyrightText() {
    Text(
        text = "© 2026 Artisancode. Todos los derechos reservados.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}

@Composable
private fun MadeWithText() {
    Text(
        text = "Hecho con Kotlin & Jetpack Compose",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SocialButton(
    icon: ImageVector,
    contentDescription: String,
    tooltip: String,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    TooltipBox(
        tooltip = tooltip,
        showTooltip = isHovered
    ) {
        OutlinedIconButton(
            onClick = onClick,
            modifier = Modifier
                .size(40.dp)
                .pointerInput(Unit) {
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
                .scale(if (isHovered) 1.15f else 1.0f),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}