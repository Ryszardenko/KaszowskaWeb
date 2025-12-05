package com.machmudow.kaszowska.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.zIndex
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.pdf.openPdfInNewTab

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PdfViewerButton(
    pdfUrl: String = "price_list.pdf",
    buttonText: String = "Zobacz Cennik"
) {
    var isHovered by remember { mutableStateOf(false) }
    var showModal by remember { mutableStateOf(false) }

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
                .clickable { showModal = true }
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

    // PDF Modal
    PdfModal(
        isVisible = showModal,
        pdfUrl = pdfUrl,
        onDismiss = { showModal = false }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PdfModal(
    isVisible: Boolean,
    pdfUrl: String,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        // Full-screen dimmed background with padding
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1000f)
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(40.dp) // Padding creates the transparent dimmed border
        ) {
            // Close button in top-right corner (outside the white card)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopEnd)
            ) {
                CloseButton(onClick = onDismiss)
            }

            // PDF content area with animation
            AnimatedVisibility(
                visible = isVisible,
                modifier = Modifier.fillMaxSize(),
                enter = scaleIn(
                    initialScale = 0.95f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(400)),
                exit = scaleOut(
                    targetScale = 0.95f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Text(
                        text = "CENNIK",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light,
                        color = KaszowskaColors.TextDark,
                        letterSpacing = 4.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // PDF content area - fills remaining space
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        KaszowskaColors.SoftBeige.copy(alpha = 0.15f),
                                        KaszowskaColors.Gold.copy(alpha = 0.08f),
                                        KaszowskaColors.SoftBeige.copy(alpha = 0.15f)
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(32.dp)
                        ) {
                            Text(
                                text = "📄",
                                fontSize = 80.sp
                            )

                            Text(
                                text = "Podgląd cennika",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = KaszowskaColors.TextDark,
                                letterSpacing = 1.sp
                            )

                            Text(
                                text = "Kliknij poniżej, aby otworzyć pełny cennik w nowej karcie",
                                fontSize = 15.sp,
                                color = KaszowskaColors.TextLight
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OpenPdfButton(pdfUrl = pdfUrl)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OpenPdfButton(pdfUrl: String) {
    var isHovered by remember { mutableStateOf(false) }

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
            .clickable { openPdfInNewTab(pdfUrl) }
            .padding(horizontal = 40.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Otwórz PDF",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "↗",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CloseButton(onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                if (isHovered)
                    KaszowskaColors.Gold
                else
                    Color.White.copy(alpha = 0.9f)
            )
            .graphicsLayer {
                scaleX = if (isHovered) 1.1f else 1f
                scaleY = if (isHovered) 1.1f else 1f
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "✕",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = if (isHovered) Color.White else KaszowskaColors.TextDark
        )
    }
}

