package org.artisancode.mywebkotlin.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.artisancode.mywebkotlin.models.Project
import org.artisancode.mywebkotlin.models.ProjectsData
import org.artisancode.mywebkotlin.utils.RevealDirection
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.ScrollReveal
import org.artisancode.mywebkotlin.utils.rememberScreenSize
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProjectsSection(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean = false
) {
    val screenSize = rememberScreenSize()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .defaultMinSize(minHeight = 700.dp)
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
        // Título
        ScrollReveal(
            trigger = shouldAnimate,
            direction = RevealDirection.TOP,
            distance = 80,
            duration = 2000,
            delayMillis = 200
        ) {
            // ← AQUÍ VA EL if/else
            if (screenSize == ScreenSize.MOBILE) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Últimos",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Proyectos",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Últimos",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Proyectos",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Grid de proyectos
        if (screenSize == ScreenSize.DESKTOP) {
            // Desktop: 3 columnas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ProjectsData.projects.forEach { project ->
                    ProjectCard(
                        modifier = Modifier.weight(1f),
                        shouldAnimate = shouldAnimate,
                        project = project
                    )
                }
            }
        } else {
            // Mobile/Tablet: 1 columna
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ProjectsData.projects.forEach { project ->
                    ProjectCard(
                        modifier = Modifier.fillMaxWidth(),
                        shouldAnimate = shouldAnimate,
                        project = project
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean,
    project: Project
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

        val overlayOffsetY by animateFloatAsState(
            targetValue = if (isHovered) 0f else 1f,  // 0f = visible, 1f = oculto abajo
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            )
        )

        Card(
            modifier = Modifier
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
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isHovered) 16.dp else 4.dp
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Imagen
                Image(
                    painter = painterResource(project.imageResource),
                    contentDescription = project.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .scale(if (isHovered) 1.1f else 1.0f),
                    contentScale = ContentScale.Crop
                )

                // Overlay que sube desde abajo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .graphicsLayer {
                            translationY = size.height * overlayOffsetY
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color(0xFF9290C3).copy(alpha = 0.92f)  // tu --main-color
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Text(
                            text = project.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}