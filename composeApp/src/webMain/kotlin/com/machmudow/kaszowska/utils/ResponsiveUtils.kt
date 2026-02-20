package com.machmudow.kaszowska.utils

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSizeClass {
    Compact,  // Phone portrait: < 600dp
    Medium,   // Tablet / Phone landscape: 600dp - 900dp
    Expanded  // Desktop: > 900dp
}

data class WindowSize(
    val width: Dp,
    val height: Dp,
    val sizeClass: WindowSizeClass
)

val LocalWindowSize = compositionLocalOf {
    WindowSize(
        width = 1200.dp,
        height = 800.dp,
        sizeClass = WindowSizeClass.Expanded
    )
}

fun calculateWindowSizeClass(width: Dp): WindowSizeClass = when {
    width < 600.dp -> WindowSizeClass.Compact
    width < 900.dp -> WindowSizeClass.Medium
    else -> WindowSizeClass.Expanded
}

@Composable
fun ResponsiveLayout(
    modifier: Modifier = Modifier,
    content: @Composable BoxWithConstraintsScope.() -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val windowSize = remember(maxWidth) {
            WindowSize(
                width = maxWidth,
                height = maxHeight, // We still keep the height but don't trigger recomposition if ONLY height changes
                sizeClass = calculateWindowSizeClass(maxWidth)
            )
        }

        CompositionLocalProvider(LocalWindowSize provides windowSize) {
            content()
        }
    }
}

// Helper properties for common responsive values
val WindowSize.isMobile: Boolean
    get() = sizeClass == WindowSizeClass.Compact

val WindowSize.isTablet: Boolean
    get() = sizeClass == WindowSizeClass.Medium

val WindowSize.isDesktop: Boolean
    get() = sizeClass == WindowSizeClass.Expanded

val WindowSize.horizontalPadding: Dp
    get() = when (sizeClass) {
        WindowSizeClass.Compact -> 20.dp
        WindowSizeClass.Medium -> 40.dp
        WindowSizeClass.Expanded -> 80.dp
    }

val WindowSize.verticalSectionPadding: Dp
    get() = when (sizeClass) {
        WindowSizeClass.Compact -> 60.dp
        WindowSizeClass.Medium -> 60.dp
        WindowSizeClass.Expanded -> 80.dp
    }

val WindowSize.navHorizontalPadding: Dp
    get() = when (sizeClass) {
        WindowSizeClass.Compact -> 16.dp
        WindowSizeClass.Medium -> 24.dp
        WindowSizeClass.Expanded -> 40.dp
    }
