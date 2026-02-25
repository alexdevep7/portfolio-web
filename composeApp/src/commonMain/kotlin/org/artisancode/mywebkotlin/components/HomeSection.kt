package org.artisancode.mywebkotlin.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mywebkotlin.composeapp.generated.resources.Res
import mywebkotlin.composeapp.generated.resources.profile
import org.artisancode.mywebkotlin.utils.RevealDirection
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.ScrollReveal
import org.artisancode.mywebkotlin.utils.rememberScreenSize
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.milliseconds

// Colores para tecnologías
private object TechColors {
    val Kotlin = Color(0xFF7F52FF)           // Morado Kotlin
    val Java = Color(0xFFED8B00)             // Naranja Java
    val Compose = Color(0xFF4285F4)          // Azul Compose
    val XML = Color(0xFFE44D26)              // Naranja-Rojo XML/HTML
    val Material = Color(0xFF018786)         // Verde azulado Material
    val MVVM = Color(0xFF00C853)             // Verde arquitectura
    val Coroutines = Color(0xFF9C27B0)       // Púrpura Coroutines
    val Clean = Color(0xFF607D8B)            // Gris azulado Clean
    val Firebase = Color(0xFFFFCA28)         // Amarillo Firebase
    val Github = Color(0xFF939699)           // Negro Github
}

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean = false
) {
    val screenSize = rememberScreenSize()
    var isVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        delay(200.milliseconds)
        isVisible = true
    }

    if (screenSize == ScreenSize.DESKTOP) {
        // DESKTOP: Layout horizontal
        Row(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = when (screenSize) {
                        ScreenSize.MOBILE -> 600.dp
                        ScreenSize.TABLET -> 650.dp
                        else -> 700.dp
                    }
                )
                .padding(horizontal = 80.dp, vertical = 60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contenido con weight (ocupa espacio restante)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ScrollReveal(
                    trigger = shouldAnimate,
                    direction = RevealDirection.TOP,
                    duration = 2000,
                    delayMillis = 200
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Saludo
                        Text(
                            text = "Hola, mi nombre es",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // Nombre
                        Text(
                            text = "Alfredo Castillo",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // Título profesional
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Soy",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            TypedText(
                                texts = listOf(
                                    "Android Developer",
                                    "Kotlin Multiplatform Developer"
                                ),
                                style = MaterialTheme.typography.headlineMedium,
                                delayBetween = 2000L
                            )
                        }

                        // Descripción
                        Text(
                            text = "Desarrollo soluciones con Kotlin y Jetpack Compose, aplicando Clean Architecture. He construido" +
                                    "productos para móvil, tablet, desktop y web integrando Firebase y APIs REST.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            modifier = Modifier.widthIn(max = 500.dp)
                        )

                        // Redes sociales
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            SocialMediaButton(
                                icon = Icons.Default.Camera,
                                contentDescription = "Instagram",
                                tooltip = "Mi Instagram",
                                onClick = { openUrl("https://www.instagram.com/artisancode7/") }
                            )
                            SocialMediaButton(
                                icon = Icons.Default.Business,
                                contentDescription = "LinkedIn",
                                tooltip = "Mi LinkedIn",
                                onClick = { openUrl("https://www.linkedin.com/in/alfredo-castillo-408166117") }
                            )
                        }

                        // Iconos de tecnologías
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Tecnologías:",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TechChip(
                                    text = "Kotlin Multiplataforma",
                                    backgroundColor = TechColors.Kotlin.copy(alpha = 0.15f),
                                    borderColor = TechColors.Kotlin.copy(alpha = 0.4f),
                                    textColor = TechColors.Kotlin
                                )
                                TechChip(
                                    text = "Java",
                                    backgroundColor = TechColors.Java.copy(alpha = 0.15f),
                                    borderColor = TechColors.Java.copy(alpha = 0.4f),
                                    textColor = TechColors.Java
                                )
                                TechChip(
                                    text = "Jetpack Compose",
                                    backgroundColor = TechColors.Compose.copy(alpha = 0.15f),
                                    borderColor = TechColors.Compose.copy(alpha = 0.4f),
                                    textColor = TechColors.Compose
                                )
                                TechChip(
                                    text = "XML",
                                    backgroundColor = TechColors.XML.copy(alpha = 0.15f),
                                    borderColor = TechColors.XML.copy(alpha = 0.4f),
                                    textColor = TechColors.XML
                                )
                                TechChip(
                                    text = "Material 3",
                                    backgroundColor = TechColors.Material.copy(alpha = 0.15f),
                                    borderColor = TechColors.Material.copy(alpha = 0.4f),
                                    textColor = TechColors.Material
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TechChip(
                                    text = "MVVM",
                                    backgroundColor = TechColors.MVVM.copy(alpha = 0.15f),
                                    borderColor = TechColors.MVVM.copy(alpha = 0.4f),
                                    textColor = TechColors.MVVM
                                )
                                TechChip(
                                    text = "Coroutines & Flow",
                                    backgroundColor = TechColors.Coroutines.copy(alpha = 0.15f),
                                    borderColor = TechColors.Coroutines.copy(alpha = 0.4f),
                                    textColor = TechColors.Coroutines
                                )
                                TechChip(
                                    text = "Clean Architecture",
                                    backgroundColor = TechColors.Clean.copy(alpha = 0.15f),
                                    borderColor = TechColors.Clean.copy(alpha = 0.4f),
                                    textColor = TechColors.Clean
                                )
                                TechChip(
                                    text = "Firebase",
                                    backgroundColor = TechColors.Firebase.copy(alpha = 0.15f),
                                    borderColor = TechColors.Firebase.copy(alpha = 0.4f),
                                    textColor = TechColors.Firebase
                                )
                                TechChip(
                                    text = "Github",
                                    backgroundColor = TechColors.Github.copy(alpha = 0.15f),
                                    borderColor = TechColors.Github.copy(alpha = 0.4f),
                                    textColor = TechColors.Github
                                )
                            }
                        }
                    }
                }
            }

            // Imagen
            HomeImage(isVisible, offsetY, screenSize)
        }
    } else {
        // MOBILE/TABLET: Layout vertical
        Column(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = when (screenSize) {
                        ScreenSize.MOBILE -> 600.dp
                        ScreenSize.TABLET -> 650.dp
                        else -> 700.dp
                    }
                )
                .padding(
                    horizontal = if (screenSize == ScreenSize.MOBILE) 24.dp else 40.dp,
                    vertical = 40.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Imagen arriba en móvil
            HomeImage(isVisible, offsetY, screenSize)

            // Contenido
            ScrollReveal(
                trigger = shouldAnimate,
                direction = RevealDirection.TOP,
                duration = 2000,
                delayMillis = 200
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Saludo
                    Text(
                        text = "Hola, mi nombre es",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Nombre
                    Text(
                        text = "Alfredo Castillo",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Título profesional
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Soy",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        TypedText(
                            texts = listOf(
                                "Android Developer",
                                "KMP Developer"
                            ),
                            style = MaterialTheme.typography.headlineSmall,
                            delayBetween = 2000L
                        )
                    }

                    // Descripción
                    Text(
                        text = "Desarrollo aplicaciones Android escalables utilizando Kotlin y Jetpack Compose, aplicando Clean Architecture y buenas prácticas. " +
                                "He construido soluciones SaaS reales con Firebase, Room y APIs REST, enfocadas en performance y experiencia de usuario.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    // Redes sociales
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        SocialMediaButton(
                            icon = Icons.Default.Camera,
                            contentDescription = "Instagram",
                            tooltip = "Mi Instagram",
                            onClick = { openUrl("https://www.instagram.com/alexdevep/") }
                        )
                        SocialMediaButton(
                            icon = Icons.Default.Business,
                            contentDescription = "LinkedIn",
                            tooltip = "Mi LinkedIn",
                            onClick = { openUrl("https://www.linkedin.com/in/alfredo-castillo-408166117") }
                        )
                    }

                    // Iconos de tecnologías (en múltiples filas para móvil)
                    if (screenSize == ScreenSize.MOBILE) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "Kotlin Multiplataforma",
                                    TechColors.Kotlin.copy(alpha = 0.15f),
                                    TechColors.Kotlin.copy(alpha = 0.4f),
                                    TechColors.Kotlin
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "Java",
                                    TechColors.Java.copy(alpha = 0.15f),
                                    TechColors.Java.copy(alpha = 0.4f),
                                    TechColors.Java
                                )
                                TechChip(
                                    "Jetpack Compose",
                                    TechColors.Compose.copy(alpha = 0.15f),
                                    TechColors.Compose.copy(alpha = 0.4f),
                                    TechColors.Compose
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "XML",
                                    TechColors.XML.copy(alpha = 0.15f),
                                    TechColors.XML.copy(alpha = 0.4f),
                                    TechColors.XML
                                )
                                TechChip(
                                    "Material 3",
                                    TechColors.Material.copy(alpha = 0.15f),
                                    TechColors.Material.copy(alpha = 0.4f),
                                    TechColors.Material
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "MVVM",
                                    TechColors.MVVM.copy(alpha = 0.15f),
                                    TechColors.MVVM.copy(alpha = 0.4f),
                                    TechColors.MVVM
                                )
                                TechChip(
                                    "Coroutines & Flow",
                                    TechColors.Coroutines.copy(alpha = 0.15f),
                                    TechColors.Coroutines.copy(alpha = 0.4f),
                                    TechColors.Coroutines
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "Clean Architecture",
                                    TechColors.Clean.copy(alpha = 0.15f),
                                    TechColors.Clean.copy(alpha = 0.4f),
                                    TechColors.Clean
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TechChip(
                                    "Firebase",
                                    TechColors.Firebase.copy(alpha = 0.15f),
                                    TechColors.Firebase.copy(alpha = 0.4f),
                                    TechColors.Firebase
                                )
                                TechChip(
                                    "Github",
                                    TechColors.Github.copy(alpha = 0.15f),
                                    TechColors.Github.copy(alpha = 0.4f),
                                    TechColors.Github
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeImage(
    isVisible: Boolean,
    offsetY: Float,
    screenSize: ScreenSize
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { 80 },
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(
            animationSpec = tween(durationMillis = 2000)
        )
    ) {
        Box(
            modifier = Modifier.size(
                when (screenSize) {
                    ScreenSize.MOBILE -> 280.dp
                    ScreenSize.TABLET -> 350.dp
                    else -> 450.dp
                }
            ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.profile),
                contentDescription = "Alfredo Castillo",
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = offsetY.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun SocialMediaButton(
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
                .size(48.dp)
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
            border = BorderStroke(
                width = if (isHovered) 3.dp else 2.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                containerColor = if (isHovered)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else
                    Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(if (isHovered) 26.dp else 24.dp)
            )
        }
    }
}

@Composable
private fun TechChip(
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
    borderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

private fun openUrl(url: String) {
    kotlinx.browser.window.open(url, "_blank")
}