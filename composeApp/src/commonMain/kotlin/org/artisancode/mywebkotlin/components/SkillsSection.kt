package org.artisancode.mywebkotlin.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.artisancode.mywebkotlin.utils.RevealDirection
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.ScrollReveal
import org.artisancode.mywebkotlin.utils.rememberScreenSize

@Composable
fun SkillsSection(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean = false
) {
    val screenSize = rememberScreenSize()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .defaultMinSize(
                minHeight = when (screenSize) {
                    ScreenSize.MOBILE -> 600.dp
                    ScreenSize.TABLET -> 650.dp
                    else -> 700.dp
                }
            )
            .padding(
                horizontal = when (screenSize) {
                    ScreenSize.MOBILE -> 24.dp
                    ScreenSize.TABLET -> 40.dp
                    else -> 80.dp
                },
                vertical = 60.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        // Título
        ScrollReveal(
            trigger = shouldAnimate,
            direction = RevealDirection.TOP,
            distance = 80,
            duration = 2000,
            delayMillis = 200
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Habilidades y",
                    style = when (screenSize) {
                        ScreenSize.MOBILE -> MaterialTheme.typography.displaySmall
                        else -> MaterialTheme.typography.displayMedium
                    },
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Experiencia",
                    style = when (screenSize) {
                        ScreenSize.MOBILE -> MaterialTheme.typography.displaySmall
                        else -> MaterialTheme.typography.displayMedium
                    },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Grid de skills
        if (screenSize == ScreenSize.DESKTOP) {
            // Desktop: 3 columnas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SkillCard(
                    modifier = Modifier.weight(1f),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.Code,
                    title = "Web Apps",
                    description = "Desarrollo de aplicaciones web con Kotlin y Compose, enfocadas en arquitectura limpia, " +
                            "rendimiento y escalabilidad. Integración con APIs REST y Firebase."
                )
                SkillCard(
                    modifier = Modifier.weight(1f),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.PhoneAndroid,
                    title = "Mobile & Tablet Apps",
                    description = "Aplicaciones Android modernas con Kotlin y Jetpack Compose, gestión de estado con Coroutines y arquitectura MVVM."
                )
                SkillCard(
                    modifier = Modifier.weight(1f),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.DesktopMac,
                    title = "Desktop Applications",
                    description = "Aplicaciones administrativas con Kotlin y Compose Desktop, integración con bases de datos locales y servicios en la nube."
                )
            }
        } else {
            // Mobile/Tablet: 1 columna
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SkillCard(
                    modifier = Modifier.fillMaxWidth(),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.Code,
                    title = "Web Apps",
                    description = "Desarrollo de aplicaciones web con Kotlin y Compose, enfocadas en arquitectura limpia, " +
                            "rendimiento y escalabilidad. Integración con APIs REST y Firebase."
                )
                SkillCard(
                    modifier = Modifier.fillMaxWidth(),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.PhoneAndroid,
                    title = "Mobile & Tablet Apps",
                    description = "Aplicaciones Android modernas con Kotlin y Jetpack Compose, gestión de estado con Coroutines y arquitectura MVVM."
                )
                SkillCard(
                    modifier = Modifier.fillMaxWidth(),
                    shouldAnimate = shouldAnimate,
                    icon = Icons.Default.Settings,
                    title = "Desktop Applications",
                    description = "Aplicaciones administrativas con Kotlin y Compose Desktop, integración con bases de datos locales y servicios en la nube."
                )
            }
        }
    }
}

@Composable
private fun SkillCard(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean,
    icon: ImageVector,
    title: String,
    description: String
) {
    ScrollReveal(
        trigger = shouldAnimate,
        direction = RevealDirection.BOTTOM,
        distance = 80,
        duration = 2000,
        delayMillis = 200,
        modifier = modifier
    ) {
        var isHovered by remember { mutableStateOf(false) }

        val elevation by animateDpAsState(
            targetValue = if (isHovered) 12.dp else 4.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

        val iconSize by animateDpAsState(
            targetValue = if (isHovered) 72.dp else 64.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

        Card(
            modifier = Modifier
                .height(320.dp)
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
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = elevation
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}