package com.machmudow.kaszowska

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.components.imagesmodal.ImagesModal
import com.machmudow.kaszowska.sections.AboutSection
import com.machmudow.kaszowska.sections.ContactSection
import com.machmudow.kaszowska.sections.Footer
import com.machmudow.kaszowska.sections.HeroSection
import com.machmudow.kaszowska.sections.ServicesSection
import com.machmudow.kaszowska.sections.TopNavigation
import com.machmudow.kaszowska.sections.model.Section
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.theme.KaszowskaTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun App() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isScrolled =
        listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 50

    val showBackToTop = listState.firstVisibleItemIndex > 1

    // PDF modal state
    val modalImages = remember { mutableStateListOf<DrawableResource>() }

    // Initial app fade-in animation
    var isAppVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAppVisible = true
    }

    val appAlpha by animateFloatAsState(
        targetValue = if (isAppVisible) 1f else 0f,
        animationSpec = tween(800, easing = FastOutSlowInEasing)
    )

    val scrollToSection: (Section) -> Unit = { section ->
        coroutineScope.launch {
            listState.animateScrollToItem(section.index)
        }
    }

    KaszowskaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = appAlpha }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item { HeroSection() }
                item { AboutSection() }
                item {
                    ServicesSection(
                        showModalImages = { modalImages.addAll(it) },
                    )
                }
                item { ContactSection() }
                item { Footer() }
            }

            // Fixed navigation overlay
            TopNavigation(
                isScrolled = isScrolled,
                onNavigate = scrollToSection,
            )

            // Animated Back to Top button
            if (showBackToTop) {
                BackToTopButton {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            }

            AnimatedVisibility(
                visible = modalImages.isNotEmpty(),
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                ImagesModal(
                    images = modalImages,
                    onDismiss = { modalImages.clear() },
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.BackToTopButton(onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    // Floating animation
    val infiniteTransition = rememberInfiniteTransition()
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(40.dp)
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
                translationY = floatingOffset
            }
            .size(56.dp)
            .clip(CircleShape)
            .background(KaszowskaColors.Gold)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "↑",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.White
        )
    }
}
