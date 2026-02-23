package org.artisancode.mywebkotlin.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@Composable
fun TypedText(
    texts: List<String>,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    delayBetween: Long = 2000L
) {
    var currentTextIndex by remember { mutableStateOf(0) }
    var currentText by remember { mutableStateOf("") }
    var isDeleting by remember { mutableStateOf(false) }
    var showCursor by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            showCursor = !showCursor
            delay(530)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            val fullText = texts[currentTextIndex]

            if (!isDeleting) {
                if (currentText.length < fullText.length) {
                    currentText = fullText.substring(0, currentText.length + 1)
                    delay(80L)  // Más rápido: de 100L a 80L
                } else {
                    delay(delayBetween)
                    isDeleting = true
                }
            } else {
                if (currentText.isNotEmpty()) {
                    currentText = currentText.substring(0, currentText.length - 1)
                    delay(40L)  // Más rápido: de 50L a 40L
                } else {
                    isDeleting = false
                    currentTextIndex = (currentTextIndex + 1) % texts.size
                }
            }
        }
    }

    Text(
        text = currentText + if (showCursor) "|" else " ",
        modifier = modifier,
        style = style,
        color = MaterialTheme.colorScheme.primary
    )
}

