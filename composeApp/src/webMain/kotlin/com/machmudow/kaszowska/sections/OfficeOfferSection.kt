package com.machmudow.kaszowska.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.model.OfficeOffer
import com.machmudow.kaszowska.model.OfficeOfferSection as OfficeOfferSectionModel
import com.machmudow.kaszowska.model.OfficeOfferService
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.verticalSectionPadding
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response

// Shared state holder for office offer data
class OfficeOfferState {
    var officeOffer by mutableStateOf<OfficeOffer?>(null)
    var isVisible by mutableStateOf(false)
    var isLoaded by mutableStateOf(false)
}

@Composable
fun rememberOfficeOfferState(): OfficeOfferState {
    val state = remember { OfficeOfferState() }

    val json = remember {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    LaunchedEffect(Unit) {
        if (!state.isLoaded) {
            try {
                val response =
                    window.fetch("composeResources/kaszowska.composeapp.generated.resources/files/office_offer.json")
                        .await<Response>()
                val jsonString = response.text().await<String>()
                state.officeOffer = json.decodeFromString<OfficeOffer>(jsonString)
            } catch (e: Exception) {
                console.log("Error loading office_offer.json: ${e.message}")
            }
            state.isVisible = true
            state.isLoaded = true
        }
    }

    return state
}

// Part 1: Header section with title
@Composable
fun OfficeOfferHeaderSection(state: OfficeOfferState) {
    val windowSize = LocalWindowSize.current
    val officeOffer = state.officeOffer
    val isVisible = state.isVisible

    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        KaszowskaColors.White,
                        KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                        KaszowskaColors.SoftGray.copy(alpha = 0.3f)
                    )
                )
            )
            .padding(
                top = windowSize.verticalSectionPadding,
                bottom = if (windowSize.isMobile) 24.dp else 40.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Decorative element above title
            Row(
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(if (windowSize.isMobile) 30.dp else 50.dp)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    KaszowskaColors.Gold.copy(alpha = 0f),
                                    KaszowskaColors.Gold
                                )
                            )
                        )
                )
                Spacer(modifier = Modifier.width(if (windowSize.isMobile) 12.dp else 16.dp))
                Box(
                    modifier = Modifier
                        .size(if (windowSize.isMobile) 6.dp else 8.dp)
                        .clip(CircleShape)
                        .background(KaszowskaColors.Gold)
                )
                Spacer(modifier = Modifier.width(if (windowSize.isMobile) 12.dp else 16.dp))
                Box(
                    modifier = Modifier
                        .width(if (windowSize.isMobile) 30.dp else 50.dp)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    KaszowskaColors.Gold,
                                    KaszowskaColors.Gold.copy(alpha = 0f)
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 16.dp else 24.dp))

            Text(
                text = "OFERTA GABINETOWA",
                fontSize = if (windowSize.isMobile) 10.sp else 11.sp,
                fontWeight = FontWeight.Medium,
                color = KaszowskaColors.Gold,
                letterSpacing = 4.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 12.dp else 16.dp))

            Text(
                text = officeOffer?.meta?.title ?: "Oferta Gabinetowa",
                fontSize = if (windowSize.isMobile) 26.sp else 40.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 1.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            officeOffer?.meta?.subtitle?.let { subtitle ->
                Spacer(modifier = Modifier.height(if (windowSize.isMobile) 8.dp else 12.dp))
                Text(
                    text = subtitle,
                    fontSize = if (windowSize.isMobile) 13.sp else 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    color = KaszowskaColors.TextLight,
                    modifier = Modifier.graphicsLayer {
                        alpha = titleAlpha
                        translationY = titleOffset
                    }
                )
            }
        }
    }
}

// Part 2: Cards section with horizontal scroll
@Composable
fun OfficeOfferCardsSection(state: OfficeOfferState) {
    val windowSize = LocalWindowSize.current
    val officeOffer = state.officeOffer
    val isVisible = state.isVisible

    val scrollState = rememberScrollState()

    val horizontalPadding = windowSize.horizontalPadding
    val cardWidth = if (windowSize.isMobile) 340.dp else 480.dp
    val cardSpacing = if (windowSize.isMobile) 20.dp else 32.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                        KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                        KaszowskaColors.White
                    )
                )
            )
            .padding(bottom = windowSize.verticalSectionPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Horizontally scrollable cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(cardSpacing)
            ) {
                officeOffer?.sections?.forEachIndexed { index, section ->
                    OfficeOfferCard(
                        section = section,
                        modifier = Modifier.width(cardWidth),
                        isVisible = isVisible,
                        delay = 200 + index * 100,
                        isMobile = windowSize.isMobile,
                        cardIndex = index
                    )
                }
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 24.dp else 40.dp))

            // Scroll indicator
            val showScrollIndicator by remember {
                derivedStateOf {
                    scrollState.maxValue > 0
                }
            }

            // Fixed height container for scroll indicator to prevent jumping
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (windowSize.isMobile) 60.dp else 80.dp),
                contentAlignment = Alignment.Center
            ) {
                if (showScrollIndicator) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Scroll hint with arrow icons
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Left arrow icon
                            Canvas(
                                modifier = Modifier.size(if (windowSize.isMobile) 12.dp else 14.dp)
                            ) {
                                val arrowColor = KaszowskaColors.TextLight.copy(alpha = 0.6f)
                                val strokeWidth = if (size.width < 40f) 1.5f else 2f
                                val path = Path().apply {
                                    moveTo(size.width * 0.7f, size.height * 0.2f)
                                    lineTo(size.width * 0.3f, size.height * 0.5f)
                                    lineTo(size.width * 0.7f, size.height * 0.8f)
                                }
                                drawPath(
                                    path = path,
                                    color = arrowColor,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round,
                                        join = StrokeJoin.Round
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Przewiń, aby zobaczyć więcej",
                                fontSize = if (windowSize.isMobile) 10.sp else 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = KaszowskaColors.TextLight.copy(alpha = 0.6f),
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Right arrow icon
                            Canvas(
                                modifier = Modifier.size(if (windowSize.isMobile) 12.dp else 14.dp)
                            ) {
                                val arrowColor = KaszowskaColors.TextLight.copy(alpha = 0.6f)
                                val strokeWidth = if (size.width < 40f) 1.5f else 2f
                                val path = Path().apply {
                                    moveTo(size.width * 0.3f, size.height * 0.2f)
                                    lineTo(size.width * 0.7f, size.height * 0.5f)
                                    lineTo(size.width * 0.3f, size.height * 0.8f)
                                }
                                drawPath(
                                    path = path,
                                    color = arrowColor,
                                    style = Stroke(
                                        width = strokeWidth,
                                        cap = StrokeCap.Round,
                                        join = StrokeJoin.Round
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(if (windowSize.isMobile) 12.dp else 16.dp))

                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding)
                        ) {
                            val trackWidth = maxWidth
                            val baseThumbWidth = if (windowSize.isMobile) 60.dp else 100.dp
                            val thumbWidth =
                                if (trackWidth < baseThumbWidth) trackWidth else baseThumbWidth

                            val scrollFraction by remember {
                                derivedStateOf {
                                    if (scrollState.maxValue == 0) 0f
                                    else scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                                }
                            }

                            val maxOffset = (trackWidth - thumbWidth).coerceAtLeast(0.dp)
                            val thumbOffset = maxOffset * scrollFraction

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(KaszowskaColors.TextLight.copy(alpha = 0.15f))
                            )

                            Box(
                                modifier = Modifier
                                    .offset(x = thumbOffset)
                                    .width(thumbWidth)
                                    .height(2.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                KaszowskaColors.Gold.copy(alpha = 0.7f),
                                                KaszowskaColors.Gold,
                                                KaszowskaColors.Gold.copy(alpha = 0.7f)
                                            )
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Keep the original for backwards compatibility
@Deprecated("Use split sections instead: OfficeOfferHeaderSection, OfficeOfferCardsSection")
@Composable
fun OfficeOfferSection() {
    val state = rememberOfficeOfferState()

    Column(modifier = Modifier.fillMaxWidth()) {
        OfficeOfferHeaderSection(state)
        OfficeOfferCardsSection(state)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OfficeOfferCard(
    section: OfficeOfferSectionModel,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    delay: Int = 0,
    isMobile: Boolean = false,
    cardIndex: Int = 0
) {
    var isHovered by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    // Show first 2 services by default, rest are collapsible
    val visibleServicesCount = 2
    val hasMoreServices = section.services.size > visibleServicesCount
    val visibleServices =
        if (isExpanded) section.services else section.services.take(visibleServicesCount)
    val hiddenServicesCount = section.services.size - visibleServicesCount

    val cardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val cardOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 60f,
        animationSpec = tween(800, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 20f else 4f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.015f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (isHovered) 1f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val expandIconRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val cardPadding = if (isMobile) 24.dp else 36.dp

    Column(
        modifier = modifier
            .graphicsLayer {
                alpha = cardAlpha
                translationY = cardOffset
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation
            }
            .clip(RoundedCornerShape(if (isMobile) 4.dp else 8.dp))
            .background(KaszowskaColors.White)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        KaszowskaColors.Gold.copy(alpha = borderAlpha * 0.8f),
                        KaszowskaColors.Gold.copy(alpha = borderAlpha * 0.4f),
                        KaszowskaColors.Gold.copy(alpha = borderAlpha * 0.8f)
                    )
                ),
                shape = RoundedCornerShape(if (isMobile) 4.dp else 8.dp)
            )
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .padding(cardPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Card number badge
        Box(
            modifier = Modifier
                .size(if (isMobile) 28.dp else 36.dp)
                .clip(CircleShape)
                .background(
                    if (isHovered) KaszowskaColors.Gold
                    else KaszowskaColors.Gold.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${cardIndex + 1}",
                fontSize = if (isMobile) 12.sp else 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isHovered) KaszowskaColors.White else KaszowskaColors.Gold
            )
        }

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 20.dp))

        // Section title
        Text(
            text = section.title,
            fontSize = if (isMobile) 18.sp else 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        // Intro text with line clamp when collapsed
        Text(
            text = section.introText,
            fontSize = if (isMobile) 12.sp else 14.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 18.sp else 22.sp,
            textAlign = TextAlign.Center,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

        // Elegant divider
        Row(
            modifier = Modifier.fillMaxWidth(0.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                KaszowskaColors.Gold.copy(alpha = 0f),
                                KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.6f else 0.3f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.8f else 0.4f))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                KaszowskaColors.Gold.copy(alpha = if (isHovered) 0.6f else 0.3f),
                                KaszowskaColors.Gold.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

        // Services header
        Text(
            text = "ZABIEGI",
            fontSize = if (isMobile) 9.sp else 10.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        // Services list - always visible services
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(if (isMobile) 12.dp else 16.dp)
        ) {
            visibleServices.forEach { service ->
                ServiceItem(
                    service = service,
                    isMobile = isMobile,
                    isHovered = isHovered
                )
            }
        }

        // Expand/Collapse button
        if (hasMoreServices) {
            Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { isExpanded = !isExpanded }
                    .background(
                        if (isExpanded) KaszowskaColors.Gold.copy(alpha = 0.1f)
                        else KaszowskaColors.SoftGray
                    )
                    .padding(
                        horizontal = if (isMobile) 18.dp else 24.dp,
                        vertical = if (isMobile) 10.dp else 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isExpanded) "Zwiń" else "Pokaż więcej (+$hiddenServicesCount)",
                    fontSize = if (isMobile) 11.sp else 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isExpanded) KaszowskaColors.Gold else KaszowskaColors.TextDark,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "▼",
                    fontSize = if (isMobile) 8.sp else 9.sp,
                    color = if (isExpanded) KaszowskaColors.Gold else KaszowskaColors.TextDark,
                    modifier = Modifier.rotate(expandIconRotation)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ServiceItem(
    service: OfficeOfferService,
    isMobile: Boolean,
    isHovered: Boolean
) {
    var isItemHovered by remember { mutableStateOf(false) }

    val itemBackgroundAlpha by animateFloatAsState(
        targetValue = if (isItemHovered) 0.08f else 0f,
        animationSpec = tween(200)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        KaszowskaColors.Gold.copy(alpha = itemBackgroundAlpha * 0.5f),
                        KaszowskaColors.Gold.copy(alpha = itemBackgroundAlpha),
                        KaszowskaColors.Gold.copy(alpha = itemBackgroundAlpha * 0.5f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        KaszowskaColors.Gold.copy(alpha = if (isItemHovered) 0.2f else 0f),
                        KaszowskaColors.Gold.copy(alpha = if (isItemHovered) 0.1f else 0f),
                        KaszowskaColors.Gold.copy(alpha = if (isItemHovered) 0.2f else 0f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .onPointerEvent(PointerEventType.Enter) { isItemHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isItemHovered = false }
            .padding(if (isMobile) 14.dp else 18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Service indicator dot
            Box(
                modifier = Modifier
                    .size(if (isMobile) 6.dp else 7.dp)
                    .clip(CircleShape)
                    .background(
                        if (isItemHovered || isHovered) KaszowskaColors.Gold
                        else KaszowskaColors.TextLight.copy(alpha = 0.4f)
                    )
            )
            Spacer(modifier = Modifier.width(if (isMobile) 12.dp else 14.dp))

            // Service name with optional target area
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.name,
                    fontSize = if (isMobile) 14.sp else 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isItemHovered) KaszowskaColors.Gold else KaszowskaColors.TextDark,
                    letterSpacing = 0.3.sp
                )
                service.targetArea?.let { area ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = area,
                        fontSize = if (isMobile) 10.sp else 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = KaszowskaColors.Gold.copy(alpha = 0.9f),
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(if (isMobile) 8.dp else 10.dp))

        // Service description
        Text(
            text = service.description,
            fontSize = if (isMobile) 12.sp else 13.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 18.sp else 21.sp,
            modifier = Modifier.padding(start = if (isMobile) 18.dp else 21.dp)
        )
    }
}
