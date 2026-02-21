package com.machmudow.kaszowska

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.machmudow.kaszowska.components.imagesmodal.ImagesModal
import com.machmudow.kaszowska.model.AboutSectionData
import com.machmudow.kaszowska.model.AppDataContent
import com.machmudow.kaszowska.model.GroupedServices
import com.machmudow.kaszowska.model.OfficeOffer
import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.TrainingOffer
import com.machmudow.kaszowska.sections.AboutSection
import com.machmudow.kaszowska.sections.ContactSection
import com.machmudow.kaszowska.sections.Footer
import com.machmudow.kaszowska.sections.HeroSection
import com.machmudow.kaszowska.sections.ImageCarousel
import com.machmudow.kaszowska.sections.OfficeOfferCardsSection
import com.machmudow.kaszowska.sections.OfficeOfferHeaderSection
import com.machmudow.kaszowska.sections.PricesSection
import com.machmudow.kaszowska.sections.ServicesSection
import com.machmudow.kaszowska.sections.TopNavigation
import com.machmudow.kaszowska.sections.TrainingOfferCurriculumSection
import com.machmudow.kaszowska.sections.TrainingOfferHeaderSection
import com.machmudow.kaszowska.sections.TrainingOfferPackageSection
import com.machmudow.kaszowska.sections.TrainingOfferPricingSection
import com.machmudow.kaszowska.sections.TrainingOfferTrainerSection
import com.machmudow.kaszowska.sections.model.Section
import com.machmudow.kaszowska.sections.rememberOfficeOfferStateHolder
import com.machmudow.kaszowska.sections.rememberTrainingOfferStateHolder
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.theme.KaszowskaTheme
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.ResponsiveLayout
import com.machmudow.kaszowska.utils.image.officeImages
import com.machmudow.kaszowska.utils.image.workImages
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.loadRemoteJson
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.scroll_up
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.collections.orEmpty

@Composable
fun App() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 50
        }
    }

    val showBackToTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 1
        }
    }

    var appDataContent: AppDataContent? by remember {
        mutableStateOf(null)
    }

    FetchAppData(
        onLoaded = { appDataContent = it },
    )

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
        ResponsiveLayout(modifier = Modifier.fillMaxSize()) {
            val windowSize = LocalWindowSize.current

            // Shared state holders
            val officeOfferStateHolder = rememberOfficeOfferStateHolder(
                appDataContent?.officeOffer
            )
            val officeOfferState by officeOfferStateHolder.state.collectAsStateWithLifecycle()

            val trainingOfferStateHolder = rememberTrainingOfferStateHolder(
                appDataContent?.trainingOffer
            )
            val trainingOfferState by trainingOfferStateHolder.state.collectAsStateWithLifecycle()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = appAlpha }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    // 0
                    item { HeroSection() }
                    // 1
                    item { AboutSection(data = appDataContent?.aboutSection) }
                    // 2
                    item { ServicesSection(data = appDataContent?.groupedServices) }
                    // 3
                    item {
                        OfficeOfferHeaderSection(
                            state = officeOfferState,
                        )
                    }
                    // 4
                    item {
                        OfficeOfferCardsSection(
                            state = officeOfferState,
                        )
                    }
                    // 5 - TRAINING_OFER
                    item { TrainingOfferHeaderSection(trainingOfferState) }
                    // 6
                    item { TrainingOfferTrainerSection(trainingOfferState) }
                    // 7
                    item { TrainingOfferCurriculumSection(trainingOfferState) }
                    // 8
                    item { TrainingOfferPackageSection(trainingOfferState) }
                    // 9
                    item { TrainingOfferPricingSection(trainingOfferState) }
                    // 10 - PRICE_LIST
                    item {
                        PricesSection(
                            data = appDataContent?.allPrices.orEmpty(),
                        )
                    }
                    // 11
                    item {
                        val allImages = remember { officeImages + workImages }
                        ImageCarousel(
                            modifier = Modifier.padding(top = if (windowSize.isMobile) 20.dp else 40.dp),
                            images = allImages,
                            onImageClick = { image ->
                                modalImages.clear()
                                modalImages.add(image)
                            }
                        )
                    }
                    // 12 - CONTACT
                    item { ContactSection() }
                    // 13
                    item { Footer() }
                }

                // Fixed navigation overlay
                TopNavigation(
                    isScrolled = isScrolled,
                    onNavigate = scrollToSection,
                )

                // Animated Back to Top button
                BackToTopButton(
                    visible = showBackToTop,
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                )

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
}

@Composable
private fun FetchAppData(
    onLoaded: (AppDataContent) -> Unit,
) {
    LaunchedEffect(Unit) {
        val aboutSectionData = loadRemoteJson<AboutSectionData>(
            fileName = "about_section",
            remoteUrl = Constants.ABOUT_SECTION_URL,
        ).getOrNull()

        val groupedServices = loadRemoteJson<GroupedServices>(
            fileName = "grouped_services",
            remoteUrl = Constants.GROUPED_SERVICES_URL,
        ).getOrNull()

        val officeOffer = loadRemoteJson<OfficeOffer>(
            fileName = "office_offer",
            remoteUrl = Constants.OFFICE_OFFER_URL
        ).getOrNull()

        val allPrices = loadRemoteJson<List<PriceCategory>>(
            fileName = "all_prices",
            remoteUrl = Constants.ALL_PRICES_URL,
        ).getOrNull().orEmpty()

        val trainingOffer = loadRemoteJson<TrainingOffer>(
            fileName = "training_offer",
            remoteUrl = Constants.TRAINING_OFFER_URL,
        ).getOrNull()

        onLoaded(
            AppDataContent(
                aboutSection = aboutSectionData,
                groupedServices = groupedServices,
                officeOffer = officeOffer,
                allPrices = allPrices,
                trainingOffer = trainingOffer,
            ),
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.BackToTopButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val windowSize = LocalWindowSize.current

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val scale by animateFloatAsState(
        targetValue = if (visible) (if (isHovered) 1.1f else 1f) else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    // One-time floating entrance animation (from below)
    val floatingOffset by animateFloatAsState(
        targetValue = if (visible) 0f else 30f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val buttonPadding = if (windowSize.isMobile) 20.dp else 40.dp
    val buttonSize = if (windowSize.isMobile) 48.dp else 56.dp

    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(buttonPadding)
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
                translationY = floatingOffset
            }
            .size(buttonSize)
            .clip(CircleShape)
            .background(KaszowskaColors.Gold)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .clickable(enabled = visible) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(Res.drawable.scroll_up),
            contentDescription = "Back to Top",
            tint = KaszowskaColors.White,
        )
    }
}
