package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.AnimationStateHolder
import com.machmudow.kaszowska.model.GroupedServices
import com.machmudow.kaszowska.model.Service
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.loadRemoteJson
import com.machmudow.kaszowska.utils.verticalSectionPadding

@Composable
fun ServicesSection() {
    val windowSize = LocalWindowSize.current
    var servicesData by remember { mutableStateOf<GroupedServices?>(null) }

    LaunchedEffect(Unit) {
        AnimationStateHolder.servicesSectionVisible = true
        servicesData = loadRemoteJson<GroupedServices>(
            fileName = "grouped_services.json",
            remoteUrl = Constants.GROUPED_SERVICES_URL,
        ).getOrNull()
    }

    val titleAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.servicesSectionVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.servicesSectionVisible) 0f else 50f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val lazyListState = rememberLazyListState()

    val horizontalPadding = windowSize.horizontalPadding
    val cardWidth = if (windowSize.isMobile) 280.dp else 320.dp
    val cardSpacing = if (windowSize.isMobile) 16.dp else 40.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.SoftGray)
            .padding(vertical = windowSize.verticalSectionPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "USŁUGI",
                fontSize = if (windowSize.isMobile) 11.sp else 12.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.Gold,
                letterSpacing = 3.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 12.dp else 16.dp))

            Text(
                text = "Co oferuję",
                fontSize = if (windowSize.isMobile) 28.sp else 42.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 2.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 40.dp else 80.dp))

            // Original groupedServices LazyRow
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { clip = false },
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(cardSpacing)
            ) {
                servicesData?.services?.let { services ->
                    itemsIndexed(services) { index, service ->
                        ServiceCard(
                            service = service,
                            modifier = Modifier.width(cardWidth),
                            isVisible = AnimationStateHolder.servicesSectionVisible,
                            delay = 300 + index * 150,
                            isMobile = windowSize.isMobile
                        )
                    }
                }
            }

            // Calculate scroll fraction from lazy list state
            val showScrollIndicator by remember {
                derivedStateOf {
                    lazyListState.layoutInfo.totalItemsCount > 0 &&
                            (lazyListState.canScrollForward || lazyListState.canScrollBackward)
                }
            }

            if (showScrollIndicator) {
                Spacer(modifier = Modifier.height(if (windowSize.isMobile) 16.dp else 24.dp))

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                ) {
                    val trackWidth = maxWidth
                    val baseThumbWidth = if (windowSize.isMobile) 80.dp else 120.dp
                    val thumbWidth = if (trackWidth < baseThumbWidth) trackWidth else baseThumbWidth

                    val scrollFraction by remember {
                        derivedStateOf {
                            val layoutInfo = lazyListState.layoutInfo
                            val totalItemsCount = layoutInfo.totalItemsCount
                            if (totalItemsCount == 0) return@derivedStateOf 0f

                            val visibleItemsInfo = layoutInfo.visibleItemsInfo
                            if (visibleItemsInfo.isEmpty()) return@derivedStateOf 0f

                            val firstVisibleItem = visibleItemsInfo.first()

                            // Calculate total scrollable range
                            val viewportSize =
                                layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
                            val totalContentSize =
                                (totalItemsCount * (firstVisibleItem.size + cardSpacing.value)).toInt()
                            val maxScrollOffset = (totalContentSize - viewportSize).coerceAtLeast(0)

                            if (maxScrollOffset == 0) return@derivedStateOf 0f

                            // Current scroll position
                            val currentOffset =
                                firstVisibleItem.index * (firstVisibleItem.size + cardSpacing.value.toInt()) - firstVisibleItem.offset

                            (currentOffset / maxScrollOffset.toFloat()).coerceIn(0f, 1f)
                        }
                    }

                    val maxOffset = (trackWidth - thumbWidth).coerceAtLeast(0.dp)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(KaszowskaColors.SoftGray.copy(alpha = 0.6f))
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = (maxOffset * scrollFraction).toPx()
                            }
                            .width(thumbWidth)
                            .height(3.dp)
                            .background(KaszowskaColors.Gold)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ServiceCard(
    service: Service,
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
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // Pulsing gold accent on hover - only animate when hovered to save performance
    val pulseAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.8f else 0.3f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val cardPadding = if (isMobile) 24.dp else 40.dp

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
                color = if (isHovered) KaszowskaColors.Gold.copy(alpha = pulseAlpha) else KaszowskaColors.SoftGray.copy(
                    alpha = 0.3f
                )
            )
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .padding(cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated golden dot
        val dotScale by animateFloatAsState(
            targetValue = if (isHovered) 1.3f else 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Box(
            modifier = Modifier
                .size(if (isMobile) 6.dp else 8.dp)
                .graphicsLayer {
                    scaleX = dotScale
                    scaleY = dotScale
                }
                .background(KaszowskaColors.Gold)
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        Text(
            text = service.title,
            fontSize = if (isMobile) 20.sp else 24.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        Text(
            text = service.description,
            fontSize = if (isMobile) 13.sp else 14.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 20.sp else 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 32.dp))

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.3f)
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
                            KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                            KaszowskaColors.SoftGray.copy(alpha = 0.3f)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = service.price,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = if (isHovered) KaszowskaColors.Gold else KaszowskaColors.TextDark,
            letterSpacing = 1.sp
        )
    }
}
