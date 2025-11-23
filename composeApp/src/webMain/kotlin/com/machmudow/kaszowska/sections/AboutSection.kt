package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@Composable
fun AboutSection() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val imageAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val imageOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else -100f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    val contentOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 100f,
        animationSpec = tween(1200, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        KaszowskaColors.White,
                        KaszowskaColors.SoftBeige.copy(alpha = 0.3f),
                        KaszowskaColors.White
                    )
                )
            )
            .padding(vertical = 120.dp, horizontal = 80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            // Left side - Image placeholder with entrance animation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(500.dp)
                    .graphicsLayer {
                        alpha = imageAlpha
                        translationX = imageOffset
                    }
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                                KaszowskaColors.Gold.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Magdalena\nKaszowska",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextLight,
                    letterSpacing = 2.sp
                )
            }

            // Right side - Content with staggered animations
            Column(
                modifier = Modifier
                    .weight(1f)
                    .graphicsLayer {
                        alpha = contentAlpha
                        translationX = contentOffset
                    }
            ) {
                Text(
                    text = "O MNIE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Magdalena Kaszowska",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextDark,
                    letterSpacing = 2.sp,
                    lineHeight = 52.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Jestem specjalistką makijażu permanentnego z wieloletnim doświadczeniem. " +
                            "Moja pasja do perfekcji oraz dbałość o każdy szczegół sprawiają, " +
                            "że efekty moich zabiegów są naturalne i długotrwałe.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Każdy zabieg traktuję indywidualnie, dostosowując technikę i kształt " +
                            "do Twoich potrzeb i oczekiwań. Pracuję tylko na najwyższej jakości produktach, " +
                            "które zapewniają bezpieczeństwo i trwałe efekty.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Credentials or highlights with staggered entrance
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HighlightItem("Certyfikowany specjalista", isVisible, 600)
                    HighlightItem("Ponad X lat doświadczenia", isVisible, 750)
                    HighlightItem("Setki zadowolonych klientek", isVisible, 900)
                }
            }
        }
    }
}

@Composable
private fun HighlightItem(text: String, isVisible: Boolean, delay: Int) {
    val itemAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val itemOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
        animationSpec = tween(600, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    // Pulsing animation for the dot
    val infiniteTransition = rememberInfiniteTransition()
    val dotScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.graphicsLayer {
            alpha = itemAlpha
            translationY = itemOffset
        }
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .graphicsLayer {
                    scaleX = dotScale
                    scaleY = dotScale
                }
                .background(KaszowskaColors.Gold)
        )

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )
    }
}

