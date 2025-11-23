package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

@Composable
fun HeroSection() {
    // Animation states
    val infiniteTransition = rememberInfiniteTransition()

    // Continuous gradient animation
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Floating animation for particles
    val particleAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing)
        )
    )

    // Initial entrance animations
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Staggered entrance animations for text elements
    val subtitleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 200, easing = FastOutSlowInEasing)
    )

    val subtitleOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(1000, delayMillis = 200, easing = FastOutSlowInEasing)
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 500, easing = FastOutSlowInEasing)
    )

    val titleScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(1200, delayMillis = 500, easing = FastOutSlowInEasing)
    )

    val descriptionAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 1000, easing = FastOutSlowInEasing)
    )

    val descriptionOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
        animationSpec = tween(1000, delayMillis = 1000, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        KaszowskaColors.SoftBeige,
                        Color(0xFFB89F91),
                        KaszowskaColors.SoftBeige
                    ),
                    start = Offset(gradientOffset, gradientOffset),
                    end = Offset(gradientOffset + 1000f, gradientOffset + 1000f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated floating particles/shapes in background
        AnimatedBackgroundParticles(particleAnimation)

        // Subtle overlay for better text contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.1f),
                            Color.Black.copy(alpha = 0.3f)
                        )
                    )
                )
        )

        // Hero Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated subtitle
            Text(
                text = "Permanent Makeup",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = subtitleAlpha
                        translationY = subtitleOffset
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Animated main title with scale effect
            Text(
                text = "MAGDALENA\nKASZOWSKA",
                fontSize = 64.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 8.sp,
                textAlign = TextAlign.Center,
                lineHeight = 72.sp,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = titleAlpha
                        scaleX = titleScale
                        scaleY = titleScale
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Animated description
            Text(
                text = "Profesjonalny makijaż permanentny",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.9f),
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = descriptionAlpha
                        translationY = descriptionOffset
                    }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Pulsing decorative line
            PulsingDecorativeLine(isVisible)
        }
    }
}

@Composable
private fun AnimatedBackgroundParticles(animationProgress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        // Draw multiple floating circles with different speeds and sizes
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

@Composable
private fun PulsingDecorativeLine(isVisible: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val width by animateFloatAsState(
        targetValue = if (isVisible) 100f else 0f,
        animationSpec = tween(1500, delayMillis = 1200, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .width(width.dp)
            .height(2.dp)
            .background(KaszowskaColors.White.copy(alpha = pulseAlpha))
    )
}

// Extension function for viewport height
private val Int.vh: Dp
    get() = (this * 10).dp // Approximation for web
