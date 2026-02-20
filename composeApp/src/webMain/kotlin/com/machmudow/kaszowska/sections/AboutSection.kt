package com.machmudow.kaszowska.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.AnimationStateHolder
import com.machmudow.kaszowska.model.AboutSectionData
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.loadRemoteJson
import com.machmudow.kaszowska.utils.verticalSectionPadding
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.magda
import org.jetbrains.compose.resources.painterResource

@Composable
fun AboutSection() {
    val windowSize = LocalWindowSize.current
    var aboutData by remember { mutableStateOf<AboutSectionData?>(null) }

    LaunchedEffect(Unit) {
        AnimationStateHolder.aboutSectionVisible = true
        aboutData = loadRemoteJson<AboutSectionData>(
            fileName = "about_section",
            remoteUrl = Constants.ABOUT_SECTION_URL,
        ).getOrNull()
    }

    val imageAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.aboutSectionVisible) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val imageOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.aboutSectionVisible) 0f else -100f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.aboutSectionVisible) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    val contentOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.aboutSectionVisible) 0f else 100f,
        animationSpec = tween(1200, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        KaszowskaColors.White,
                        KaszowskaColors.SoftBeige.copy(alpha = 0.3f),
                        KaszowskaColors.White
                    )
                )
            )
            .padding(
                vertical = windowSize.verticalSectionPadding,
                horizontal = windowSize.horizontalPadding
            )
    ) {
        if (windowSize.isMobile) {
            // Mobile layout - stacked vertically
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .graphicsLayer {
                            alpha = imageAlpha
                            translationY = imageOffset
                        }
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                                    KaszowskaColors.Gold.copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(Res.drawable.magda),
                        contentDescription = Constants.FULL_NAME,
                        contentScale = ContentScale.Crop,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Content below
                AboutContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = contentAlpha
                            translationY = contentOffset
                        },
                    isMobile = true,
                    data = aboutData
                )
            }
        } else {
            // Desktop layout - side by side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                // Left side - Image
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 500.dp)
                        .graphicsLayer {
                            alpha = imageAlpha
                            translationX = imageOffset
                        }
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    KaszowskaColors.SoftGray.copy(alpha = 0.3f),
                                    KaszowskaColors.Gold.copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(Res.drawable.magda),
                        contentDescription = Constants.FULL_NAME,
                        contentScale = ContentScale.Crop,
                    )
                }

                // Right side - Content
                AboutContent(
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            alpha = contentAlpha
                            translationX = contentOffset
                        },
                    isMobile = false,
                    data = aboutData
                )
            }
        }
    }
}

@Composable
private fun AboutContent(
    modifier: Modifier = Modifier,
    isMobile: Boolean,
    data: AboutSectionData?
) {
    Column(modifier = modifier) {
        Text(
            text = data?.title ?: "O MNIE",
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        Text(
            text = data?.name ?: Constants.FULL_NAME,
            fontSize = if (isMobile) 28.sp else 42.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            letterSpacing = 2.sp,
            lineHeight = if (isMobile) 36.sp else 52.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 20.dp else 32.dp))

        data?.paragraphs?.forEach { paragraph ->
            Text(
                text = paragraph,
                fontSize = if (isMobile) 14.sp else 16.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.TextLight,
                lineHeight = if (isMobile) 24.sp else 28.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (data == null) {
            // Fallback text if data is still loading or failed
            Text(
                text = "Ładowanie...",
                fontSize = if (isMobile) 14.sp else 16.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.TextLight
            )
        }
    }
}
