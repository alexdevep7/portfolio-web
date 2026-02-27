package org.artisancode.mywebkotlin.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent

@Composable
fun SectionWrapper(
    onOffsetMeasured: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            // Posición Y relativa al scroll container
            val offset = coordinates.positionInParent().y.toInt()
            onOffsetMeasured(offset)
        }
    ) {
        content()
    }
}