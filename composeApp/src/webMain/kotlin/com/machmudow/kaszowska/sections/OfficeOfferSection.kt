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
import androidx.compose.foundation.layout.offset
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

@Composable
fun OfficeOfferSection() {
    val windowSize = LocalWindowSize.current

    var isVisible by remember { mutableStateOf(false) }
    var officeOffer by remember { mutableStateOf<OfficeOffer?>(null) }

    val json = remember {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    LaunchedEffect(Unit) {
        try {
            val response = window.fetch("composeResources/kaszowska.composeapp.generated.resources/files/office_offer.json").await<Response>()
            val jsonString = response.text().await<String>()
            officeOffer = json.decodeFromString<OfficeOffer>(jsonString)
        } catch (e: Exception) {
            console.log("Error loading office_offer.json: ${e.message}")
        }
        isVisible = true
    }

    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val lazyListState = rememberLazyListState()

    val horizontalPadding = windowSize.horizontalPadding
    val cardWidth = if (windowSize.isMobile) 300.dp else 400.dp
    val cardSpacing = if (windowSize.isMobile) 16.dp else 40.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.White)
            .padding(vertical = windowSize.verticalSectionPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "OFERTA GABINETOWA",
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
                text = officeOffer?.meta?.title ?: "Oferta Gabinetowa",
                fontSize = if (windowSize.isMobile) 28.sp else 42.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 2.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            officeOffer?.meta?.subtitle?.let { subtitle ->
                Spacer(modifier = Modifier.height(if (windowSize.isMobile) 8.dp else 12.dp))
                Text(
                    text = subtitle,
                    fontSize = if (windowSize.isMobile) 14.sp else 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    modifier = Modifier.graphicsLayer {
                        alpha = titleAlpha
                        translationY = titleOffset
                    }
                )
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 40.dp else 80.dp))

            // Horizontally scrollable cards
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { clip = false },
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(cardSpacing)
            ) {
                officeOffer?.sections?.let { sections ->
                    itemsIndexed(sections) { index, section ->
                        OfficeOfferCard(
                            section = section,
                            modifier = Modifier.width(cardWidth),
                            isVisible = isVisible,
                            delay = 300 + index * 150,
                            isMobile = windowSize.isMobile
                        )
                    }
                }
            }

            // Scroll indicator
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

                            val viewportSize =
                                layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
                            val totalContentSize =
                                (totalItemsCount * (firstVisibleItem.size + cardSpacing.value)).toInt()
                            val maxScrollOffset = (totalContentSize - viewportSize).coerceAtLeast(0)

                            if (maxScrollOffset == 0) return@derivedStateOf 0f

                            val currentOffset =
                                firstVisibleItem.index * (firstVisibleItem.size + cardSpacing.value.toInt()) - firstVisibleItem.offset

                            (currentOffset / maxScrollOffset.toFloat()).coerceIn(0f, 1f)
                        }
                    }

                    val maxOffset = (trackWidth - thumbWidth).coerceAtLeast(0.dp)
                    val thumbOffset = maxOffset * scrollFraction

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(KaszowskaColors.SoftGray.copy(alpha = 0.6f))
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = thumbOffset)
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
private fun OfficeOfferCard(
    section: OfficeOfferSectionModel,
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
        targetValue = if (isHovered) 1.02f else 1f,
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
            .background(KaszowskaColors.SoftGray)
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
        // Golden dot
        Box(
            modifier = Modifier
                .size(if (isMobile) 6.dp else 8.dp)
                .background(KaszowskaColors.Gold)
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        // Section title
        Text(
            text = section.title,
            fontSize = if (isMobile) 18.sp else 22.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.TextDark,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        // Intro text
        Text(
            text = section.introText,
            fontSize = if (isMobile) 12.sp else 13.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 18.sp else 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        // Divider
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.5f)
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
                            KaszowskaColors.TextLight.copy(alpha = 0.2f),
                            KaszowskaColors.TextLight.copy(alpha = 0.2f)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        // Services list
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(if (isMobile) 12.dp else 16.dp)
        ) {
            section.services.forEach { service ->
                ServiceItem(
                    service = service,
                    isMobile = isMobile,
                    isHovered = isHovered
                )
            }
        }
    }
}

@Composable
private fun ServiceItem(
    service: OfficeOfferService,
    isMobile: Boolean,
    isHovered: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        // Service name with optional target area
        Text(
            text = buildString {
                append(service.name)
                service.targetArea?.let { area ->
                    append(" • $area")
                }
            },
            fontSize = if (isMobile) 13.sp else 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isHovered) KaszowskaColors.Gold else KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Service description
        Text(
            text = service.description,
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 16.sp else 18.sp
        )
    }
}
