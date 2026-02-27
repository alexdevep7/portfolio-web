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
import org.artisancode.mywebkotlin.utils.SectionWrapper
import org.artisancode.mywebkotlin.utils.rememberScreenSize
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun App() {
    AppTheme {
        val scrollState = rememberScrollState()
        val screenSize = rememberScreenSize()
        val coroutineScope = rememberCoroutineScope()

        val visibleSections = remember { mutableStateMapOf<String, Boolean>() }

        // Posiciones reales medidas dinámicamente
        val sectionOffsets = remember { mutableStateMapOf<String, Int>() }

        val currentSection by remember {
            derivedStateOf {
                val scroll = scrollState.value
                val sorted = sectionOffsets.entries.sortedBy { it.value }
                sorted.lastOrNull { scroll >= (it.value - 150) }?.key ?: "home"
            }
        }

        LaunchedEffect(scrollState.value) {
            val scroll = scrollState.value

            if (!visibleSections.contains("home")) visibleSections["home"] = true

            sectionOffsets.forEach { (section, offset) ->
                if (scroll > offset - 200 && !visibleSections.contains(section)) {
                    visibleSections[section] = true
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
                            val targetPosition = sectionOffsets[section] ?: 0
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
                    // Wrapper que mide la posición Y real de cada sección
                    SectionWrapper(
                        onOffsetMeasured = { sectionOffsets["home"] = it }
                    ) {
                        HomeSection(
                            modifier = Modifier.fillMaxWidth(),
                            shouldAnimate = visibleSections["home"] == true
                        )
                    }

                    SectionWrapper(
                        onOffsetMeasured = { sectionOffsets["about"] = it }
                    ) {
                        AboutSection(
                            modifier = Modifier.fillMaxWidth(),
                            shouldAnimate = visibleSections["about"] == true
                        )
                    }

                    SectionWrapper(
                        onOffsetMeasured = { sectionOffsets["skills"] = it }
                    ) {
                        SkillsSection(
                            modifier = Modifier.fillMaxWidth(),
                            shouldAnimate = visibleSections["skills"] == true
                        )
                    }

                    SectionWrapper(
                        onOffsetMeasured = { sectionOffsets["projects"] = it }
                    ) {
                        ProjectsSection(
                            modifier = Modifier.fillMaxWidth(),
                            shouldAnimate = visibleSections["projects"] == true
                        )
                    }

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