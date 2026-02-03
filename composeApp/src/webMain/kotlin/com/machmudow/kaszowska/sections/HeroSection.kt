package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.isMobile
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.logo_main
import org.jetbrains.compose.resources.painterResource
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI

@Composable
fun HeroSection() {
    val windowSize = LocalWindowSize.current

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
private fun ScrollIndicator() {
    val infiniteTransition = rememberInfiniteTransition()

    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.graphicsLayer {
            translationY = bounce
        }
    ) {
        Text(
            text = "SCROLL",
            fontSize = 10.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.White.copy(alpha = alpha),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Animated arrow
        Canvas(
            modifier = Modifier
                .size(20.dp, 30.dp)
                .graphicsLayer { this.alpha = alpha }
        ) {
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width / 2, size.height)
                lineTo(0f, size.height * 0.4f)
                moveTo(size.width / 2, size.height)
                lineTo(size.width, size.height * 0.4f)
            }
            drawPath(
                path = path,
                color = Color.White,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )
        }
    }
}

// Extension function for viewport height
private val Int.vh: Dp
    get() = (this * 10).dp // Approximation for web
