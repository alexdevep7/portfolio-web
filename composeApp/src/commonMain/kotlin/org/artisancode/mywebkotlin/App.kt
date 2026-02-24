package org.artisancode.mywebkotlin

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.artisancode.mywebkotlin.components.*
import org.artisancode.mywebkotlin.ui.theme.AppTheme
import org.artisancode.mywebkotlin.utils.ScreenSize
import org.artisancode.mywebkotlin.utils.rememberScreenSize
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun App() {
    AppTheme {
        val scrollState = rememberScrollState()
        val screenSize = rememberScreenSize()
        val coroutineScope = rememberCoroutineScope()

        // Detectar qué sección está visible
        val visibleSections = remember { mutableStateMapOf<String, Boolean>() }

        // Posiciones aproximadas de cada sección (ajusta según tu layout)
        val sectionPositions = remember(screenSize) {
            when (screenSize) {
                ScreenSize.MOBILE -> mapOf(
                    "home" to 0,
                    "about" to 1944,
                    "skills" to 3878,
                    "projects" to 6428
                )
                ScreenSize.TABLET -> mapOf(
                    "home" to 0,
                    "about" to 1944,
                    "skills" to 3878,
                    "projects" to 6428
                )
                ScreenSize.DESKTOP -> mapOf(
                    "home" to 0,
                    "about" to 1511,
                    "skills" to 2825,
                    "projects" to 4221
                )
            }
        }

        LaunchedEffect(scrollState.value) {
            console.log("Scroll position: ${scrollState.value}")
            val scrollPosition = scrollState.value

            // Activar home siempre al inicio
            if (!visibleSections.contains("home")) {
                visibleSections["home"] = true
            }

            // Activar about
            val aboutTrigger = when (screenSize) {
                ScreenSize.MOBILE, ScreenSize.TABLET -> 1744
                ScreenSize.DESKTOP -> 1311
            }
            if (scrollPosition > aboutTrigger && !visibleSections.contains("about")) {
                visibleSections["about"] = true
            }

            // Activar skills
            val skillsTrigger = when (screenSize) {
                ScreenSize.MOBILE, ScreenSize.TABLET -> 3678
                ScreenSize.DESKTOP -> 2625
            }
            if (scrollPosition > skillsTrigger && !visibleSections.contains("skills")) {
                visibleSections["skills"] = true
            }

            // Activar projects
            val projectsTrigger = when (screenSize) {
                ScreenSize.MOBILE, ScreenSize.TABLET -> 6228
                ScreenSize.DESKTOP -> 4021
            }
            if (scrollPosition > projectsTrigger && !visibleSections.contains("projects")) {
                visibleSections["projects"] = true
            }
        }

        // Detectar sección actual
        val currentSection by remember {
            derivedStateOf {
                val scrollPosition = scrollState.value
                val aboutThreshold = when (screenSize) {
                    ScreenSize.MOBILE, ScreenSize.TABLET -> 1844
                    ScreenSize.DESKTOP -> 1411
                }
                val skillsThreshold = when (screenSize) {
                    ScreenSize.MOBILE, ScreenSize.TABLET -> 3778
                    ScreenSize.DESKTOP -> 2725
                }
                val projectsThreshold = when (screenSize) {
                    ScreenSize.MOBILE, ScreenSize.TABLET -> 6328
                    ScreenSize.DESKTOP -> 4121
                }

                when {
                    scrollPosition < aboutThreshold -> "home"
                    scrollPosition < skillsThreshold -> "about"
                    scrollPosition < projectsThreshold -> "skills"
                    else -> "projects"
                }
            }
        }

        val showScrollToTop by remember {
            derivedStateOf { scrollState.value > 300 }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Header(
                    currentSection = currentSection,
                    onNavigate = { section ->
                        coroutineScope.launch {
                            visibleSections[section] = true
                            delay(50.milliseconds)

                            val targetPosition = sectionPositions[section] ?: 0
                            scrollState.animateScrollTo(
                                value = targetPosition,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow,
                                    visibilityThreshold = 0.5f
                                )
                            )
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    HomeSection(
                        modifier = Modifier.fillMaxWidth(),
                        shouldAnimate = visibleSections["home"] == true
                    )
                    AboutSection(
                        modifier = Modifier.fillMaxWidth(),
                        shouldAnimate = visibleSections["about"] == true
                    )
                    SkillsSection(
                        modifier = Modifier.fillMaxWidth(),
                        shouldAnimate = visibleSections["skills"] == true
                    )
                    ProjectsSection(
                        modifier = Modifier.fillMaxWidth(),
                        shouldAnimate = visibleSections["projects"] == true
                    )
                    Footer(modifier = Modifier.fillMaxWidth())
                }
            }

            // Botón Scroll to Top (sin cambios)
            AnimatedVisibility(
                visible = showScrollToTop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
                enter = fadeIn(animationSpec = tween(300)) +
                        scaleIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300)) +
                        scaleOut(animationSpec = tween(300))
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(
                                value = 0,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMediumLow
                                )
                            )
                        }
                    },
                    modifier = Modifier.padding(
                        when (screenSize) {
                            ScreenSize.MOBILE -> 16.dp
                            else -> 32.dp
                        }
                    ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 12.dp,
                        hoveredElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Scroll to top",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}