package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .background(KaszowskaColors.White)
            .padding(vertical = 120.dp, horizontal = 40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            // Left side - Image placeholder with entrance animation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(600.dp)
                    .padding(end = 60.dp)
                    .graphicsLayer {
                        alpha = imageAlpha
                        translationX = imageOffset
                    }
                    .background(KaszowskaColors.SoftBeige)
            ) {
                // Placeholder for image
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "[PHOTO]",
                        color = KaszowskaColors.TextLight,
                        fontSize = 14.sp
                    )
                }
            }

            // Right side - Content with staggered animations
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp)
                    .graphicsLayer {
                        alpha = contentAlpha
                        translationX = contentOffset
                    },
                verticalArrangement = Arrangement.Center
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
                            "Moją pasją jest podkreślanie naturalnego piękna i pomaganie kobietom w budowaniu pewności siebie.",
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
                    HighlightItem("Certyfikowany specjalista", isVisible, 0)
                    HighlightItem("Ponad X lat doświadczenia", isVisible, 150)
                    HighlightItem("Setki zadowolonych klientek", isVisible, 300)
                }
            }
        }
    }
}

@Composable
private fun HighlightItem(text: String, isVisible: Boolean = false, delay: Int = 0) {
    val itemAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800, delayMillis = 1200 + delay, easing = FastOutSlowInEasing)
    )

    val itemOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
        animationSpec = tween(800, delayMillis = 1200 + delay, easing = FastOutSlowInEasing)
    )

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
            translationX = itemOffset
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

