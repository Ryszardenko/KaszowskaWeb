package com.machmudow.kaszowska.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReactiveButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconRes: DrawableResource? = null,
) {
    var isHovered by remember { mutableStateOf(false) }

    // One-time entrance animation
    var hasAnimated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        hasAnimated = true
    }

    // Entrance pulse animation (plays once)
    val entranceScale by animateFloatAsState(
        targetValue = if (hasAnimated) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val entranceAlpha by animateFloatAsState(
        targetValue = if (hasAnimated) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    // Hover-triggered animations
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.08f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.7f else 0.4f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = entranceScale
                scaleY = entranceScale
                alpha = entranceAlpha
            }
    ) {
        // Glowing background effect
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
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
                if (iconRes != null) {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
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
