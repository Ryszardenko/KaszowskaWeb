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
import com.machmudow.kaszowska.AnimationStateHolder
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.navHorizontalPadding
import com.machmudow.kaszowska.utils.email.openWindow

@Composable
fun Footer() {
    val windowSize = LocalWindowSize.current

    LaunchedEffect(Unit) {
        AnimationStateHolder.footerVisible = true
    }

    val isVisible = AnimationStateHolder.footerVisible

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val offset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val verticalPadding = if (windowSize.isMobile) 40.dp else 60.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.DarkBrown)
            .padding(vertical = verticalPadding, horizontal = windowSize.navHorizontalPadding)
            .graphicsLayer {
                this.alpha = alpha
                translationY = offset
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Golden line with one-time entrance animation
            val targetLineWidth = if (windowSize.isMobile) 80f else 100f
            val lineWidth by animateFloatAsState(
                targetValue = if (isVisible) targetLineWidth else 0f,
                animationSpec = tween(1500, delayMillis = 300, easing = FastOutSlowInEasing)
            )

            Box(
                modifier = Modifier
                    .width(lineWidth.dp)
                    .height(1.dp)
                    .background(KaszowskaColors.Gold.copy(alpha = 0.5f))
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 24.dp else 32.dp))

            Text(
                text = Constants.FULL_NAME.uppercase(),
                fontSize = if (windowSize.isMobile) 14.sp else 18.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = if (windowSize.isMobile) 2.sp else 3.sp
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 12.dp else 16.dp))

            Text(
                text = "Permanent Makeup Artist",
                fontSize = if (windowSize.isMobile) 11.sp else 13.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 24.dp else 32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(if (windowSize.isMobile) 24.dp else 32.dp)
            ) {
                FooterLink("Instagram") {
                    openWindow(url = Constants.INSTAGRAM_URL)
                }
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 32.dp else 40.dp))

            Text(
                text = "© 2026 ${Constants.FULL_NAME}. All rights reserved.",
                fontSize = if (windowSize.isMobile) 10.sp else 11.sp,
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

