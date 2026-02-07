package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.model.PackageItem
import com.machmudow.kaszowska.model.PricingVariant
import com.machmudow.kaszowska.model.TrainingOffer
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.verticalSectionPadding
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.ic_check
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.w3c.fetch.Response

// Shared state holder for training offer data
class TrainingOfferState {
    var trainingOffer by mutableStateOf<TrainingOffer?>(null)
    var isVisible by mutableStateOf(false)
    var isLoaded by mutableStateOf(false)
}

@Composable
fun rememberTrainingOfferState(): TrainingOfferState {
    val state = remember { TrainingOfferState() }

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
                    window.fetch("composeResources/kaszowska.composeapp.generated.resources/files/training_offer.json")
                        .await<Response>()
                val jsonString = response.text().await<String>()
                state.trainingOffer = json.decodeFromString<TrainingOffer>(jsonString)
            } catch (e: Exception) {
                console.log("Error loading training_offer.json: ${e.message}")
            }
            state.isVisible = true
            state.isLoaded = true
        }
    }

    return state
}

// Part 1: Header with title and hero subsection
@Composable
fun TrainingOfferHeaderSection(state: TrainingOfferState) {
    val windowSize = LocalWindowSize.current
    val trainingOffer = state.trainingOffer
    val isVisible = state.isVisible

    val titleAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val horizontalPadding = windowSize.horizontalPadding

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        KaszowskaColors.Cream,
                        KaszowskaColors.SoftGray.copy(alpha = 0.5f),
                        KaszowskaColors.Cream
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
            DecorativeDivider(
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                },
                isMobile = windowSize.isMobile
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 16.dp else 24.dp))

            Text(
                text = "OFERTA SZKOLENIOWA",
                fontSize = if (windowSize.isMobile) 10.sp else 11.sp,
                fontWeight = FontWeight.Medium,
                color = KaszowskaColors.Gold,
                letterSpacing = 4.sp,
                modifier = Modifier.graphicsLayer {
                    alpha = titleAlpha
                }
            )

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 12.dp else 16.dp))

            // Course title
            Text(
                text = trainingOffer?.courseMeta?.title ?: "Szkolenie PMU",
                fontSize = if (windowSize.isMobile) 26.sp else 40.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .graphicsLayer {
                        alpha = titleAlpha
                    }
            )

            trainingOffer?.courseMeta?.let { meta ->
                Spacer(modifier = Modifier.height(if (windowSize.isMobile) 8.dp else 12.dp))
                Row(
                    modifier = Modifier.graphicsLayer {
                        alpha = titleAlpha
                    },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = meta.level,
                        fontSize = if (windowSize.isMobile) 12.sp else 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = KaszowskaColors.TextLight
                    )
                    Text(
                        text = " • ",
                        fontSize = if (windowSize.isMobile) 12.sp else 14.sp,
                        color = KaszowskaColors.Gold
                    )
                    Text(
                        text = meta.method,
                        fontSize = if (windowSize.isMobile) 12.sp else 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = KaszowskaColors.Gold
                    )
                }
            }

            Spacer(modifier = Modifier.height(if (windowSize.isMobile) 40.dp else 60.dp))

            // Hero Section
            trainingOffer?.heroSection?.let { hero ->
                HeroSubsection(
                    headline = hero.headline,
                    subheadline = hero.subheadline,
                    ctaText = hero.ctaText,
                    isVisible = isVisible,
                    isMobile = windowSize.isMobile,
                    horizontalPadding = horizontalPadding
                )
            }
        }
    }
}

// Part 2: Trainer Bio Section
@Composable
fun TrainingOfferTrainerSection(state: TrainingOfferState) {
    val windowSize = LocalWindowSize.current
    val trainingOffer = state.trainingOffer
    val isVisible = state.isVisible
    val horizontalPadding = windowSize.horizontalPadding

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.Cream)
            .padding(vertical = if (windowSize.isMobile) 24.dp else 40.dp)
    ) {
        trainingOffer?.trainerBio?.let { bio ->
            TrainerBioSection(
                name = bio.name,
                role = bio.role,
                description = bio.description,
                stats = bio.stats,
                isVisible = isVisible,
                isMobile = windowSize.isMobile,
                horizontalPadding = horizontalPadding
            )
        }
    }
}

// Part 3: Curriculum Section
@Composable
fun TrainingOfferCurriculumSection(state: TrainingOfferState) {
    val windowSize = LocalWindowSize.current
    val trainingOffer = state.trainingOffer
    val isVisible = state.isVisible
    val horizontalPadding = windowSize.horizontalPadding

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        KaszowskaColors.Cream,
                        KaszowskaColors.SoftGray.copy(alpha = 0.5f),
                        KaszowskaColors.Cream
                    )
                )
            )
            .padding(vertical = if (windowSize.isMobile) 24.dp else 40.dp)
    ) {
        trainingOffer?.curriculum?.let { curriculum ->
            CurriculumSection(
                title = curriculum.title,
                modules = curriculum.modules,
                isVisible = isVisible,
                isMobile = windowSize.isMobile,
                horizontalPadding = horizontalPadding
            )
        }
    }
}

// Part 4: Package Includes Section
@Composable
fun TrainingOfferPackageSection(state: TrainingOfferState) {
    val windowSize = LocalWindowSize.current
    val trainingOffer = state.trainingOffer
    val isVisible = state.isVisible
    val horizontalPadding = windowSize.horizontalPadding

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.Cream)
            .padding(vertical = if (windowSize.isMobile) 24.dp else 40.dp)
    ) {
        trainingOffer?.packageIncludes?.let { packageIncludes ->
            PackageIncludesSection(
                title = packageIncludes.title,
                items = packageIncludes.items,
                isVisible = isVisible,
                isMobile = windowSize.isMobile,
                horizontalPadding = horizontalPadding
            )
        }
    }
}

// Part 5: Pricing Section
@Composable
fun TrainingOfferPricingSection(state: TrainingOfferState) {
    val windowSize = LocalWindowSize.current
    val trainingOffer = state.trainingOffer
    val isVisible = state.isVisible
    val horizontalPadding = windowSize.horizontalPadding

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        KaszowskaColors.Cream,
                        KaszowskaColors.SoftGray.copy(alpha = 0.5f),
                        KaszowskaColors.White
                    )
                )
            )
            .padding(
                top = if (windowSize.isMobile) 24.dp else 40.dp,
                bottom = windowSize.verticalSectionPadding
            )
    ) {
        trainingOffer?.pricingOptions?.let { pricing ->
            PricingSection(
                location = pricing.location,
                groupSize = pricing.groupSize,
                variants = pricing.variants,
                isVisible = isVisible,
                isMobile = windowSize.isMobile,
                horizontalPadding = horizontalPadding
            )
        }
    }
}

// Keep the original for backwards compatibility but mark as deprecated
@Deprecated("Use split sections instead: TrainingOfferHeaderSection, TrainingOfferTrainerSection, TrainingOfferCurriculumSection, TrainingOfferPackageSection, TrainingOfferPricingSection")
@Composable
fun TrainingOfferSection() {
    val state = rememberTrainingOfferState()

    Column(modifier = Modifier.fillMaxWidth()) {
        TrainingOfferHeaderSection(state)
        TrainingOfferTrainerSection(state)
        TrainingOfferCurriculumSection(state)
        TrainingOfferPackageSection(state)
        TrainingOfferPricingSection(state)
    }
}

@Composable
private fun DecorativeDivider(
    modifier: Modifier = Modifier,
    isMobile: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(if (isMobile) 30.dp else 50.dp)
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
        Spacer(modifier = Modifier.width(if (isMobile) 12.dp else 16.dp))
        Box(
            modifier = Modifier
                .size(if (isMobile) 6.dp else 8.dp)
                .clip(CircleShape)
                .background(KaszowskaColors.Gold)
        )
        Spacer(modifier = Modifier.width(if (isMobile) 12.dp else 16.dp))
        Box(
            modifier = Modifier
                .width(if (isMobile) 30.dp else 50.dp)
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
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HeroSubsection(
    headline: String,
    subheadline: String,
    ctaText: String,
    isVisible: Boolean,
    isMobile: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    var isButtonHovered by remember { mutableStateOf(false) }

    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 200, easing = FastOutSlowInEasing)
    )

    val buttonScale by animateFloatAsState(
        targetValue = if (isButtonHovered) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .graphicsLayer {
                alpha = contentAlpha
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Headline
        Text(
            text = headline,
            fontSize = if (isMobile) 20.sp else 32.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            color = KaszowskaColors.DarkBrown,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            lineHeight = if (isMobile) 28.sp else 42.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        // Subheadline
        Text(
            text = subheadline,
            fontSize = if (isMobile) 14.sp else 18.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            textAlign = TextAlign.Center,
            lineHeight = if (isMobile) 22.sp else 28.sp,
            modifier = Modifier.fillMaxWidth(if (isMobile) 1f else 0.7f)
        )

        Spacer(modifier = Modifier.height(if (isMobile) 28.dp else 40.dp))

        // CTA Button
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = buttonScale
                    scaleY = buttonScale
                }
                .clip(RoundedCornerShape(if (isMobile) 24.dp else 32.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            KaszowskaColors.Gold,
                            KaszowskaColors.Gold.copy(alpha = 0.9f)
                        )
                    )
                )
                .onPointerEvent(PointerEventType.Enter) { isButtonHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isButtonHovered = false }
                .clickable { /* TODO: Handle CTA click */ }
                .padding(
                    horizontal = if (isMobile) 32.dp else 48.dp,
                    vertical = if (isMobile) 14.dp else 18.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ctaText,
                fontSize = if (isMobile) 14.sp else 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = KaszowskaColors.White,
                letterSpacing = 1.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TrainerBioSection(
    name: String,
    role: String,
    description: String,
    stats: List<String>,
    isVisible: Boolean,
    isMobile: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 400, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .graphicsLayer {
                alpha = contentAlpha
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TWÓJ TRENER",
            fontSize = if (isMobile) 10.sp else 11.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

        // Trainer card
        Box(
            modifier = Modifier
                .fillMaxWidth(if (isMobile) 1f else 0.7f)
                .clip(RoundedCornerShape(if (isMobile) 12.dp else 16.dp))
                .background(KaszowskaColors.White)
                .border(
                    width = 1.dp,
                    color = KaszowskaColors.Gold.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(if (isMobile) 12.dp else 16.dp)
                )
                .padding(if (isMobile) 24.dp else 40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Name
                Text(
                    text = name,
                    fontSize = if (isMobile) 22.sp else 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = KaszowskaColors.TextDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(if (isMobile) 8.dp else 12.dp))

                // Role
                Text(
                    text = role,
                    fontSize = if (isMobile) 12.sp else 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .width(if (isMobile) 60.dp else 80.dp)
                        .height(1.dp)
                        .background(KaszowskaColors.Gold.copy(alpha = 0.3f))
                )

                Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

                // Description
                Text(
                    text = description,
                    fontSize = if (isMobile) 13.sp else 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = if (isMobile) 20.sp else 24.sp
                )

                Spacer(modifier = Modifier.height(if (isMobile) 24.dp else 32.dp))

                // Stats badges
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        if (isMobile) 8.dp else 12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(if (isMobile) 8.dp else 12.dp)
                ) {
                    stats.forEach { stat ->
                        StatBadge(stat = stat, isMobile = isMobile)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBadge(
    stat: String,
    isMobile: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(KaszowskaColors.Gold.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                color = KaszowskaColors.Gold.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = if (isMobile) 14.dp else 18.dp,
                vertical = if (isMobile) 8.dp else 10.dp
            )
    ) {
        Text(
            text = stat,
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 0.3.sp
        )
    }
}

@Composable
private fun CurriculumSection(
    title: String,
    modules: List<String>,
    isVisible: Boolean,
    isMobile: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 600, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .graphicsLayer {
                alpha = contentAlpha
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PROGRAM SZKOLENIA",
            fontSize = if (isMobile) 10.sp else 11.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        Text(
            text = title,
            fontSize = if (isMobile) 20.sp else 28.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 28.dp else 40.dp))

        // Curriculum grid - single column on mobile, two columns on desktop
        if (isMobile) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                modules.forEachIndexed { index, module ->
                    CurriculumItem(
                        index = index + 1,
                        module = module,
                        isMobile = true,
                        isVisible = isVisible,
                        delay = 700 + index * 50
                    )
                }
            }
        } else {
            // Two columns on desktop
            val leftColumn = modules.filterIndexed { index, _ -> index % 2 == 0 }
            val rightColumn = modules.filterIndexed { index, _ -> index % 2 == 1 }

            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    leftColumn.forEachIndexed { index, module ->
                        val originalIndex = index * 2
                        CurriculumItem(
                            index = originalIndex + 1,
                            module = module,
                            isMobile = false,
                            isVisible = isVisible,
                            delay = 700 + originalIndex * 50
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rightColumn.forEachIndexed { index, module ->
                        val originalIndex = index * 2 + 1
                        CurriculumItem(
                            index = originalIndex + 1,
                            module = module,
                            isMobile = false,
                            isVisible = isVisible,
                            delay = 700 + originalIndex * 50
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CurriculumItem(
    index: Int,
    module: String,
    isMobile: Boolean,
    isVisible: Boolean,
    delay: Int
) {
    var isHovered by remember { mutableStateOf(false) }

    val itemAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.08f else 0f,
        animationSpec = tween(200)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = itemAlpha }
            .clip(RoundedCornerShape(8.dp))
            .background(KaszowskaColors.Gold.copy(alpha = backgroundAlpha))
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .padding(if (isMobile) 12.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkmark icon
        Box(
            modifier = Modifier
                .size(if (isMobile) 24.dp else 28.dp)
                .clip(CircleShape)
                .background(
                    if (isHovered) KaszowskaColors.Gold
                    else KaszowskaColors.Gold.copy(alpha = 0.15f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_check),
                contentDescription = null,
                modifier = Modifier.size(if (isMobile) 14.dp else 16.dp),
                tint = if (isHovered) KaszowskaColors.White else KaszowskaColors.Gold
            )
        }

        Spacer(modifier = Modifier.width(if (isMobile) 12.dp else 16.dp))

        Text(
            text = module,
            fontSize = if (isMobile) 13.sp else 15.sp,
            fontWeight = FontWeight.Normal,
            color = if (isHovered) KaszowskaColors.TextDark else KaszowskaColors.TextLight,
            lineHeight = if (isMobile) 18.sp else 22.sp
        )
    }
}

@Composable
private fun PackageIncludesSection(
    title: String,
    items: List<PackageItem>,
    isVisible: Boolean,
    isMobile: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 800, easing = FastOutSlowInEasing)
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = contentAlpha
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "W PAKIECIE",
            fontSize = if (isMobile) 10.sp else 11.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        Text(
            text = title,
            fontSize = if (isMobile) 20.sp else 28.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )

        Spacer(modifier = Modifier.height(if (isMobile) 28.dp else 40.dp))

        // Horizontal scrolling package cards - centered when not scrolling
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(if (isMobile) 16.dp else 24.dp)
            ) {
                items.forEachIndexed { index, item ->
                    PackageCard(
                        item = item,
                        index = index,
                        isMobile = isMobile,
                        isVisible = isVisible
                    )
                }
            }
        }

        // Scroll indicator
        val showScrollIndicator by remember {
            derivedStateOf { scrollState.maxValue > 0 }
        }

        if (showScrollIndicator) {
            Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
            ) {
                val trackWidth = maxWidth
                val baseThumbWidth = if (isMobile) 60.dp else 100.dp
                val thumbWidth = if (trackWidth < baseThumbWidth) trackWidth else baseThumbWidth

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PackageCard(
    item: PackageItem,
    index: Int,
    isMobile: Boolean,
    isVisible: Boolean
) {
    var isHovered by remember { mutableStateOf(false) }

    val cardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 900 + index * 100, easing = FastOutSlowInEasing)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 12f else 2f,
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

    Column(
        modifier = Modifier
            .width(if (isMobile) 260.dp else 300.dp)
            .graphicsLayer {
                alpha = cardAlpha
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation
            }
            .clip(RoundedCornerShape(if (isMobile) 12.dp else 16.dp))
            .background(KaszowskaColors.White)
            .border(
                width = 1.dp,
                color = if (isHovered) KaszowskaColors.Gold.copy(alpha = 0.5f)
                else KaszowskaColors.SoftBeige,
                shape = RoundedCornerShape(if (isMobile) 12.dp else 16.dp)
            )
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .padding(if (isMobile) 20.dp else 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon placeholder
        Box(
            modifier = Modifier
                .size(if (isMobile) 48.dp else 56.dp)
                .clip(CircleShape)
                .background(
                    if (isHovered) KaszowskaColors.Gold
                    else KaszowskaColors.Gold.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${index + 1}",
                fontSize = if (isMobile) 18.sp else 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (isHovered) KaszowskaColors.White else KaszowskaColors.Gold
            )
        }

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 20.dp))

        Text(
            text = item.item,
            fontSize = if (isMobile) 16.sp else 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = KaszowskaColors.TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isMobile) 8.dp else 12.dp))

        Text(
            text = item.details,
            fontSize = if (isMobile) 12.sp else 14.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            textAlign = TextAlign.Center,
            lineHeight = if (isMobile) 18.sp else 22.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PricingSection(
    location: String,
    groupSize: String,
    variants: List<PricingVariant>,
    isVisible: Boolean,
    isMobile: Boolean,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 1000, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .graphicsLayer {
                alpha = contentAlpha
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CENNIK SZKOLENIA",
            fontSize = if (isMobile) 10.sp else 11.sp,
            fontWeight = FontWeight.Medium,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        Text(
            text = "Wybierz pakiet dla siebie",
            fontSize = if (isMobile) 20.sp else 28.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))

        // Location and group size
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📍 $location",
                fontSize = if (isMobile) 12.sp else 14.sp,
                color = KaszowskaColors.TextLight
            )
            Text(
                text = " • ",
                fontSize = if (isMobile) 12.sp else 14.sp,
                color = KaszowskaColors.Gold
            )
            Text(
                text = "👥 $groupSize",
                fontSize = if (isMobile) 12.sp else 14.sp,
                color = KaszowskaColors.TextLight
            )
        }

        Spacer(modifier = Modifier.height(if (isMobile) 32.dp else 48.dp))

        // Pricing cards - stack vertically on mobile
        if (isMobile) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                variants.forEach { variant ->
                    PricingCard(
                        variant = variant,
                        isMobile = true,
                        isVisible = isVisible,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
            ) {
                variants.forEach { variant ->
                    PricingCard(
                        variant = variant,
                        isMobile = false,
                        isVisible = isVisible,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PricingCard(
    variant: PricingVariant,
    isMobile: Boolean,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    var isHovered by remember { mutableStateOf(false) }

    val cardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800, delayMillis = 1100, easing = FastOutSlowInEasing)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered || variant.isRecommended) 16f else 4f,
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

    val borderColor = if (variant.isRecommended) KaszowskaColors.Gold
    else if (isHovered) KaszowskaColors.Gold.copy(alpha = 0.5f)
    else KaszowskaColors.SoftBeige

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = cardAlpha
                    scaleX = scale
                    scaleY = scale
                    shadowElevation = elevation
                }
                .clip(RoundedCornerShape(if (isMobile) 16.dp else 20.dp))
                .background(KaszowskaColors.White)
                .border(
                    width = if (variant.isRecommended) 2.dp else 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(if (isMobile) 16.dp else 20.dp)
                )
                .onPointerEvent(PointerEventType.Enter) { isHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isHovered = false }
                .padding(if (isMobile) 24.dp else 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Recommended badge
            if (variant.isRecommended) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(KaszowskaColors.Gold)
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "POLECANE",
                        fontSize = if (isMobile) 10.sp else 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = KaszowskaColors.White,
                        letterSpacing = 2.sp
                    )
                }
                Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 20.dp))
            }

            // Variant name
            Text(
                text = variant.name,
                fontSize = if (isMobile) 18.sp else 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = KaszowskaColors.TextDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(if (isMobile) 8.dp else 12.dp))

            // Duration
            Text(
                text = variant.duration,
                fontSize = if (isMobile) 13.sp else 15.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.Gold,
                textAlign = TextAlign.Center
            )

            // Description (if available)
            variant.description?.let { desc ->
                Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 16.dp))
                Text(
                    text = desc,
                    fontSize = if (isMobile) 12.sp else 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    textAlign = TextAlign.Center,
                    lineHeight = if (isMobile) 18.sp else 20.sp
                )
            }

            Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

            // Price
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${variant.priceNetto}",
                    fontSize = if (isMobile) 36.sp else 48.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextDark
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${variant.currency} netto",
                    fontSize = if (isMobile) 12.sp else 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    modifier = Modifier.padding(bottom = if (isMobile) 6.dp else 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(1.dp)
                    .background(KaszowskaColors.SoftBeige)
            )

            Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 28.dp))

            // Features list
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(if (isMobile) 10.dp else 14.dp)
            ) {
                variant.features.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_check),
                            contentDescription = null,
                            modifier = Modifier.size(if (isMobile) 16.dp else 18.dp),
                            tint = KaszowskaColors.Gold
                        )
                        Spacer(modifier = Modifier.width(if (isMobile) 10.dp else 12.dp))
                        Text(
                            text = feature,
                            fontSize = if (isMobile) 13.sp else 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = KaszowskaColors.TextLight,
                            lineHeight = if (isMobile) 18.sp else 20.sp
                        )
                    }
                }
            }
        }
    }
}
