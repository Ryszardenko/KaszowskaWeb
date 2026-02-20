package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.AnimationStateHolder
import com.machmudow.kaszowska.components.PriceCategoryCard
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.verticalSectionPadding
import com.machmudow.kaszowska.model.PriceCategory

@Composable
fun PricesSection(data: List<PriceCategory>) {
    val windowSize = LocalWindowSize.current

    LaunchedEffect(Unit) {
        AnimationStateHolder.pricesSectionVisible = true
    }

    val titleAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.pricesSectionVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val titleOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.pricesSectionVisible) 0f else 50f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val offerLazyListState = rememberLazyListState()

    val horizontalPadding = windowSize.horizontalPadding
    val offerCardWidth = if (windowSize.isMobile) 300.dp else 380.dp
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
                text = "SZCZEGÓŁOWA OFERTA",
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
                text = "Cennik usług",
                fontSize = if (windowSize.isMobile) 24.sp else 36.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 2.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                    translationY = titleOffset
                }
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 32.dp else 60.dp))

            // Offer Categories LazyRow
            LazyRow(
                state = offerLazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { clip = false },
                contentPadding = PaddingValues(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(cardSpacing)
            ) {
                itemsIndexed(data) { index, category ->
                    PriceCategoryCard(
                        category = category,
                        modifier = Modifier.width(offerCardWidth),
                        isVisible = AnimationStateHolder.pricesSectionVisible,
                        delay = 500 + index * 150,
                        isMobile = windowSize.isMobile
                    )
                }
            }

            // Scroll indicator for offer categories
            val showOfferScrollIndicator by remember {
                derivedStateOf {
                    offerLazyListState.layoutInfo.totalItemsCount > 0 &&
                            (offerLazyListState.canScrollForward || offerLazyListState.canScrollBackward)
                }
            }

            if (showOfferScrollIndicator) {
                Spacer(modifier = Modifier.height(if (windowSize.isMobile) 16.dp else 24.dp))

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                ) {
                    val trackWidth = maxWidth
                    val baseThumbWidth = if (windowSize.isMobile) 80.dp else 120.dp
                    val thumbWidth = if (trackWidth < baseThumbWidth) trackWidth else baseThumbWidth

                    val offerScrollFraction by remember {
                        derivedStateOf {
                            val layoutInfo = offerLazyListState.layoutInfo
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

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(KaszowskaColors.SoftGray.copy(alpha = 0.6f))
                    )

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = (maxOffset * offerScrollFraction).toPx()
                            }
                            .width(thumbWidth)
                            .height(3.dp)
                            .background(KaszowskaColors.Gold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 32.dp else 60.dp))
        }
    }
}
