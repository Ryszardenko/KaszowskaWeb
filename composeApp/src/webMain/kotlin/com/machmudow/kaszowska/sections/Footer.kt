package com.machmudow.kaszowska.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.email.openWindow

@Composable
fun Footer() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val offset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.DarkBrown)
            .padding(vertical = 60.dp, horizontal = 40.dp)
            .graphicsLayer {
                this.alpha = alpha
                translationY = offset
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pulsing golden line at top
            val infiniteTransition = rememberInfiniteTransition()
            val lineWidth by infiniteTransition.animateFloat(
                initialValue = 50f,
                targetValue = 100f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .width(lineWidth.dp)
                    .height(1.dp)
                    .background(KaszowskaColors.Gold.copy(alpha = 0.5f))
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = Constants.FULL_NAME.uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Permanent Makeup Artist",
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                FooterLink("Instagram") {
                    openWindow(url = Constants.INSTAGRAM_URL)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "© 2026 ${Constants.FULL_NAME}. All rights reserved.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.5f),
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }

    val textColor by animateColorAsState(
        targetValue = if (isHovered) KaszowskaColors.Gold else KaszowskaColors.White.copy(alpha = 0.8f),
        animationSpec = tween(300)
    )

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Text(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = textColor,
        letterSpacing = 1.5.sp,
    )
}

