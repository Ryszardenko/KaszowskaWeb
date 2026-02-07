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
import com.machmudow.kaszowska.sections.rememberOfficeOfferState
import com.machmudow.kaszowska.sections.rememberTrainingOfferStateHolder
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.theme.KaszowskaTheme
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.ResponsiveLayout
import com.machmudow.kaszowska.utils.image.officeImages
import com.machmudow.kaszowska.utils.image.workImages
import com.machmudow.kaszowska.utils.isMobile
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.scroll_up
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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
        ResponsiveLayout(modifier = Modifier.fillMaxSize()) {
            val windowSize = LocalWindowSize.current

            // Shared state holders
            val officeOfferState = rememberOfficeOfferState()
            val trainingOfferStateHolder = rememberTrainingOfferStateHolder()
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
                    item { HeroSection() }                                      // 0
                    item { AboutSection() }                                     // 1
                    item {                                                      // 2
                        ServicesSection(
                            showModalImages = { modalImages.addAll(it) },
                        )
                    }
                    item { OfficeOfferHeaderSection(officeOfferState) }         // 3 - OFFICE_OFER
                    item { OfficeOfferCardsSection(officeOfferState) }          // 4
                    item { TrainingOfferHeaderSection(trainingOfferState) }     // 5 - TRAINING_OFER
                    item { TrainingOfferTrainerSection(trainingOfferState) }    // 6
                    item { TrainingOfferCurriculumSection(trainingOfferState) } // 7
                    item { TrainingOfferPackageSection(trainingOfferState) }    // 8
                    item { TrainingOfferPricingSection(trainingOfferState) }    // 9
                    item { PricesSection() }                                    // 10 - PRICE_LIST
                    item {                                                      // 11
                        ImageCarousel(
                            modifier = Modifier.padding(top = if (windowSize.isMobile) 20.dp else 40.dp),
                            images = officeImages + workImages,
                            onImageClick = { image ->
                                modalImages.clear()
                                modalImages.add(image)
                            }
                        )
                    }
                    item { ContactSection() }                                   // 12 - CONTACT
                    item { Footer() }                                           // 13
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
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.BackToTopButton(onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    val windowSize = LocalWindowSize.current

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

    // One-time floating entrance animation (from below)
    val floatingOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
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
            .clickable { onClick() },
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
