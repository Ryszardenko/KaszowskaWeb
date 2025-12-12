package com.machmudow.kaszowska.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReactiveButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    var isHovered by remember { mutableStateOf(false) }

    // Pulsing animation for attention
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box {
        // Glowing background effect
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = if (isHovered) 1.1f else pulseScale
                    scaleY = if (isHovered) 1.1f else pulseScale
                    alpha = glowAlpha
                }
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            KaszowskaColors.Gold.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(4.dp)
        )

        // Main button
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = if (isHovered) 1.05f else 1f
                    scaleY = if (isHovered) 1.05f else 1f
                }
                .clip(RoundedCornerShape(25.dp))
                .background(
                    if (isHovered) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                KaszowskaColors.Gold,
                                KaszowskaColors.Gold.copy(alpha = 0.8f)
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(
                                KaszowskaColors.Gold.copy(alpha = 0.9f),
                                KaszowskaColors.Gold
                            )
                        )
                    }
                )
                .onPointerEvent(PointerEventType.Enter) { isHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isHovered = false }
                .clickable { onClick() }
                .padding(horizontal = 40.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📄",
                    fontSize = 20.sp
                )
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
