package org.artisancode.mywebkotlin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mywebkotlin.composeapp.generated.resources.Res
import mywebkotlin.composeapp.generated.resources.profile2
import org.artisancode.mywebkotlin.utils.RevealDirection
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.ScrollReveal
import org.artisancode.mywebkotlin.utils.rememberScreenSize
import org.jetbrains.compose.resources.painterResource

@Composable
fun AboutSection(
    modifier: Modifier = Modifier,
    shouldAnimate: Boolean = false
) {
    val screenSize = rememberScreenSize()

    if (screenSize == ScreenSize.DESKTOP) {
        // DESKTOP: Layout horizontal (imagen izquierda, contenido derecha)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
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
            // Imagen izquierda
            ScrollReveal(
                trigger = shouldAnimate,
                direction = RevealDirection.LEFT,
                duration = 2000,
                delayMillis = 200
            ) {
                Box(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(end = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.profile2),
                        contentDescription = "Alfredo Castillo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Contenido derecha
            Column(modifier = Modifier.weight(1f)) {
                ScrollReveal(
                    trigger = shouldAnimate,
                    direction = RevealDirection.RIGHT,
                    duration = 2000,
                    delayMillis = 400
                ) {
                    AboutContent(screenSize)
                }
            }
        }
    } else {
        // MOBILE/TABLET: Layout vertical
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .defaultMinSize(minHeight = 600.dp)
                .padding(
                    horizontal = if (screenSize == ScreenSize.MOBILE) 24.dp else 40.dp,
                    vertical = 40.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Imagen arriba
            ScrollReveal(
                trigger = shouldAnimate,
                direction = RevealDirection.TOP,
                duration = 2000,
                delayMillis = 200
            ) {
                Box(
                    modifier = Modifier.size(
                        when (screenSize) {
                            ScreenSize.MOBILE -> 280.dp
                            else -> 350.dp
                        }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.profile2),
                        contentDescription = "Alfredo Castillo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Contenido abajo
            ScrollReveal(
                trigger = shouldAnimate,
                direction = RevealDirection.BOTTOM,
                duration = 2000,
                delayMillis = 400
            ) {
                AboutContent(screenSize)
            }
        }
    }
}

@Composable
private fun AboutContent(screenSize: ScreenSize) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = if (screenSize != ScreenSize.DESKTOP)
            Alignment.CenterHorizontally
        else
            Alignment.Start
    ) {
        // Título
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Sobre",
                style = when (screenSize) {
                    ScreenSize.MOBILE -> MaterialTheme.typography.displaySmall
                    else -> MaterialTheme.typography.displayMedium
                },
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Mí",
                style = when (screenSize) {
                    ScreenSize.MOBILE -> MaterialTheme.typography.displaySmall
                    else -> MaterialTheme.typography.displayMedium
                },
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Subtítulo
        Text(
            text = "Desarrollador especializado en Kotlin y Jetpack Compose",
            style = when (screenSize) {
                ScreenSize.MOBILE -> MaterialTheme.typography.titleMedium
                else -> MaterialTheme.typography.titleLarge
            },
            color = MaterialTheme.colorScheme.primary
        )

        // Descripción - Párrafo 1
        Text(
            text = "Desarrollo aplicaciones modernas aplicando Clean Architecture y buenas prácticas de ingeniería de " +
                    "software. Construyo soluciones escalables que integran APIs REST, Firebase y bases de datos locales " +
                    "como Room, priorizando rendimiento y mantenibilidad. ",
            style = when (screenSize) {
                ScreenSize.MOBILE -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        // Descripción - Párrafo 2
        Text(
            text = "He trabajado en productos orientados a usuarios finales y soluciones empresariales, desarrollando " +
                    "versiones para dispositivos móviles, tablets, aplicaciones de escritorio tipo admin y aplicaciones web, " +
                    "participando en todo el ciclo de vida del software: arquitectura, implementación y despliegue en " +
                    "producción.",
            style = when (screenSize) {
                ScreenSize.MOBILE -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        // Descripción - Párrafo 3
        Text(
            text = "Me enfoco en escribir código limpio, modular y testeable, cuidando tanto la calidad técnica como la " +
                    "experiencia de usuario. ",
            style = when (screenSize) {
                ScreenSize.MOBILE -> MaterialTheme.typography.bodyMedium
                else -> MaterialTheme.typography.bodyLarge
            },
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        // Email
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "alexbassist2013@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Botón
        var isButtonHovered by remember { mutableStateOf(false) }

        Button(
            onClick = {
                kotlinx.browser.window.open(
                    "https://sites.google.com/view/miscertificadospm/inicio",
                    "_blank"
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = if (isButtonHovered) 8.dp else 2.dp
            ),
            modifier = Modifier
                .padding(top = 8.dp)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Enter -> isButtonHovered = true
                                PointerEventType.Exit -> isButtonHovered = false
                            }
                        }
                    }
                }
                .scale(if (isButtonHovered) 1.05f else 1.0f)
        ) {
            Text("Ver Certificados")
        }
    }
}