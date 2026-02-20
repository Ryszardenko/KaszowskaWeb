package com.machmudow.kaszowska.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem
import com.machmudow.kaszowska.theme.KaszowskaColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PriceCategoryCard(
    category: PriceCategory,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    delay: Int = 0,
    isMobile: Boolean = false
) {
    var isHovered by remember { mutableStateOf(false) }

    val cardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val cardOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 80f,
        animationSpec = tween(800, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 16f else 2f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.03f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val pulseAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.8f else 0.3f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val cardPadding = if (isMobile) 20.dp else 32.dp

    Column(
        modifier = modifier
            .graphicsLayer {
                alpha = cardAlpha
                translationY = cardOffset
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation
            }
            .background(KaszowskaColors.White)
            .border(
                width = 1.dp,
                color = if (isHovered) KaszowskaColors.Gold.copy(alpha = pulseAlpha)
                else KaszowskaColors.SoftGray.copy(alpha = 0.3f)
            )
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .padding(cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top gold accent line
        Box(
            modifier = Modifier
                .width(if (isMobile) 40.dp else 60.dp)
                .height(2.dp)
                .background(
                    if (isHovered) KaszowskaColors.Gold
                    else KaszowskaColors.Gold.copy(alpha = 0.6f)
                )
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        // Category title
        Text(
            text = category.name,
            fontSize = if (isMobile) 18.sp else 22.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            lineHeight = if (isMobile) 24.sp else 28.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

        // Divider
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.4f)
                .background(
                    if (isHovered)
                        Brush.horizontalGradient(
                            colors = listOf(
                                KaszowskaColors.Gold.copy(alpha = 0f),
                                KaszowskaColors.Gold,
                                KaszowskaColors.Gold.copy(alpha = 0f)
                            )
                        )
                    else Brush.horizontalGradient(
                        colors = listOf(
                            KaszowskaColors.SoftGray.copy(alpha = 0.2f),
                            KaszowskaColors.SoftGray.copy(alpha = 0.4f),
                            KaszowskaColors.SoftGray.copy(alpha = 0.2f)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

        // Services list
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            category.items.forEachIndexed { index, item ->
                PriceItemRow(
                    item = item,
                    isHovered = isHovered,
                    isMobile = isMobile
                )

                // Fancy divider between items (not after the last one)
                if (index < category.items.lastIndex) {
                    Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        KaszowskaColors.Gold.copy(alpha = 0f),
                                        KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.4f else 0.2f),
                                        KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.4f else 0.2f),
                                        KaszowskaColors.Gold.copy(alpha = 0f)
                                    )
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))
                }
            }
        }
    }
}

@Composable
private fun PriceItemRow(
    item: PriceItem,
    isHovered: Boolean,
    isMobile: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Small dot
        Box(
            modifier = Modifier
                .padding(
                    horizontal = if (isMobile) 10.dp else 12.dp
                )
                .size(if (isMobile) 4.dp else 5.dp)
                .background(
                    if (isHovered) KaszowskaColors.Gold
                    else KaszowskaColors.Gold.copy(alpha = 0.5f)
                )
        )

        Column(
            modifier = Modifier.fillMaxWidth(0.6f),
        ) {
            // Name (bold and bigger) - constrained width
            Text(
                text = item.name,
                fontSize = if (isMobile) 14.sp else 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = KaszowskaColors.TextDark,
                lineHeight = if (isMobile) 18.sp else 20.sp,
            )

            // Description (smaller, below name) - if exists
            if (item.description.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = item.description,
                        fontSize = if (isMobile) 11.sp else 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = KaszowskaColors.TextLight,
                        lineHeight = if (isMobile) 15.sp else 16.sp
                    )
                }
            }
        }

        // Dotted line separator - fills remaining space
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = if (isMobile) 4.dp else 8.dp)
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            KaszowskaColors.Gold.copy(alpha = 0f),
                            KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.5f else 0.3f),
                            KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.5f else 0.3f),
                            KaszowskaColors.Gold.copy(alpha = 0f)
                        )
                    )
                )
        )

        // Price
        Text(
            text = item.price,
            fontSize = if (isMobile) 13.sp else 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isHovered) KaszowskaColors.Gold else KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )
    }
}

