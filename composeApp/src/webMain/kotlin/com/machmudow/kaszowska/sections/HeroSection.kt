package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.isMobile
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.logo_main
import kaszowska.composeapp.generated.resources.magda_side
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HeroSection() {
    val windowSize = LocalWindowSize.current

    // Initial entrance animations
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }


    // One-time particle animation (animates from 0 to 180 degrees on load)
    val particleAnimation by animateFloatAsState(
        targetValue = if (isVisible) 180f else 0f,
        animationSpec = tween(3000, easing = FastOutSlowInEasing)
    )

    // Staggered entrance animations for text elements
    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 500, easing = FastOutSlowInEasing)
    )

    val titleScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(1200, delayMillis = 500, easing = FastOutSlowInEasing)
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF141514))
        )

        // Background image - Fit on desktop (shows full photo with black sides), Crop on mobile
        Image(
            painter = painterResource(Res.drawable.magda_side),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = if (windowSize.isMobile) ContentScale.Crop else ContentScale.Fit,
            alignment = if (windowSize.isMobile) Alignment.Center else Alignment.TopEnd,
        )

        // Subtle overlay for better text contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        // Animated background particles (on top of image)
        AnimatedBackgroundParticles(particleAnimation)

        val logoFraction = if (windowSize.isMobile) 0.85f else 0.6f

        Image(
            modifier = Modifier
                .fillMaxSize(logoFraction)
                .graphicsLayer {
                    alpha = titleAlpha
                    scaleX = titleScale
                    scaleY = titleScale
                },
            painter = painterResource(Res.drawable.logo_main),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White)
        )

    }
}

@Composable
private fun AnimatedBackgroundParticles(animationProgress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Draw circles that animate into position on load
        for (i in 0..8) {
            val angle = (animationProgress + i * 40f) * (PI / 180f).toFloat()
            val radius = 150f + i * 80f
            val x = centerX + cos(angle) * radius
            val y = centerY + sin(angle) * radius
            val circleSize = 20f + i * 10f

            drawCircle(
                color = Color.White.copy(alpha = 0.05f + i * 0.01f),
                radius = circleSize,
                center = Offset(x, y)
            )
        }

        // Draw some static elegant shapes
        for (i in 0..15) {
            val angle = i * 24f * (PI / 180f).toFloat()
            val radius = 400f
            val x = centerX + cos(angle) * radius
            val y = centerY + sin(angle) * radius

            drawCircle(
                color = Color.White.copy(alpha = 0.03f),
                radius = 8f,
                center = Offset(x, y)
            )
        }
    }
}

// Extension function for viewport height
private val Int.vh: Dp
    get() = (this * 10).dp // Approximation for web
