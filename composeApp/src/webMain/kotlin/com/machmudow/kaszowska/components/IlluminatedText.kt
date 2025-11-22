package com.machmudow.kaszowska.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IlluminatedText(
    text: String,
    fontSize: TextUnit = 13.sp,
    isScrolled: Boolean,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.96f
            isHovered -> 1.05f
            else -> 1f
        },
        animationSpec = tween(durationMillis = 120),
        label = "navItemScale",
    )

    val color = when {
        isHovered -> KaszowskaColors.Gold
        isScrolled -> KaszowskaColors.TextDark
        else -> KaszowskaColors.White
    }

    Text(
        modifier = Modifier
            .scale(scale)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { onClick() }
            .padding(vertical = 8.dp)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        color = color,
        letterSpacing = 1.5.sp,
    )
}
