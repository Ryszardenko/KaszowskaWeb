package com.machmudow.kaszowska.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.components.IlluminatedText
import com.machmudow.kaszowska.sections.model.Section
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.email.openWindow
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.navHorizontalPadding
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.ic_instagram
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopNavigation(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit
) {
    val windowSize = LocalWindowSize.current
    var isDrawerOpen by remember { mutableStateOf(false) }

    val targetColor = if (isScrolled) {
        KaszowskaColors.White.copy(alpha = 0.95f)
    } else {
        KaszowskaColors.SoftBeige.copy(alpha = 0.01f)
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "topNavBackground",
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        // Main navigation bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(
                    horizontal = windowSize.navHorizontalPadding,
                    vertical = if (windowSize.isMobile) 16.dp else 20.dp,
                ),
        ) {
            if (windowSize.isMobile) {
                // Mobile layout with hamburger menu
                MobileNavigation(
                    isScrolled = isScrolled,
                    onMenuClick = { isDrawerOpen = true },
                    onLogoClick = { onNavigate(Section.HERO) }
                )
            } else {
                // Desktop layout
                DesktopNavigation(
                    isScrolled = isScrolled,
                    onNavigate = onNavigate
                )
            }
        }

        // Mobile drawer overlay
        if (windowSize.isMobile) {
            NavigationDrawer(
                isOpen = isDrawerOpen,
                onDismiss = { isDrawerOpen = false },
                onNavigate = { section ->
                    isDrawerOpen = false
                    onNavigate(section)
                }
            )
        }
    }
}

@Composable
private fun MobileNavigation(
    isScrolled: Boolean,
    onMenuClick: () -> Unit,
    onLogoClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IlluminatedText(
            text = Constants.FULL_NAME.uppercase(),
            isScrolled = isScrolled,
            fontSize = 11.sp,
        ) {
            onLogoClick()
        }

        // Hamburger menu icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onMenuClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(if (isScrolled) KaszowskaColors.TextDark else KaszowskaColors.White)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DesktopNavigation(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit
) {
    val windowSize = LocalWindowSize.current
    val useTwoLines = windowSize.width < 1200.dp

    if (useTwoLines) {
        // Two-line layout for smaller screens
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FirstNameItem(
                isScrolled = isScrolled,
                onNavigate = onNavigate,
            )

            // Second row: Navigation items
            NavigationItems(
                isScrolled = isScrolled,
                onNavigate = onNavigate,
                spacing = 24.dp
            )
        }
    } else {
        // Single-line layout for larger screens
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FirstNameItem(
                isScrolled = isScrolled,
                onNavigate = onNavigate,
            )

            Spacer(modifier = Modifier.weight(1f))

            NavigationItems(
                isScrolled = isScrolled,
                onNavigate = onNavigate,
                spacing = 32.dp
            )
        }
    }
}

@Composable
private fun FirstNameItem(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit,
) {
    IlluminatedText(
        text = Constants.FULL_NAME.uppercase(),
        isScrolled = isScrolled,
    ) {
        onNavigate(Section.HERO)
    }
}

@Composable
private fun NavigationItems(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit,
    spacing: Dp
) {
    val windowSize = LocalWindowSize.current
    val wrapLongItems = windowSize.width < 900.dp

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Section.entries
            .drop(1) // drop Hero
            .forEach { section ->
                IlluminatedText(
                    text = section.title.uppercase(),
                    isScrolled = isScrolled,
                    wrapWords = wrapLongItems,
                ) {
                    onNavigate(section)
                }
            }

        InstagramIcon(isScrolled = isScrolled)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InstagramIcon(isScrolled: Boolean) {
    var isHovered by remember { mutableStateOf(false) }

    val color = when {
        isHovered -> KaszowskaColors.Gold
        isScrolled -> KaszowskaColors.TextDark
        else -> KaszowskaColors.White
    }

    Image(
        painter = painterResource(Res.drawable.ic_instagram),
        contentDescription = "Instagram",
        modifier = Modifier
            .size(20.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                openWindow(url = Constants.INSTAGRAM_URL)
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        colorFilter = ColorFilter.tint(color)
    )
}

@Composable
private fun NavigationDrawer(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onNavigate: (Section) -> Unit
) {
    // Backdrop
    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDismiss() }
        )
    }

    // Drawer
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(KaszowskaColors.White)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { /* Consume clicks */ }
                .padding(vertical = 40.dp, horizontal = 24.dp)
        ) {
            Column {
                // Header
                Text(
                    text = Constants.FULL_NAME.uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(KaszowskaColors.Gold)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Navigation items
                Section.entries.forEach { section ->
                    DrawerNavItem(
                        text = section.title.uppercase(),
                        onClick = { onNavigate(section) },
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(KaszowskaColors.SoftBeige)
                )

                Spacer(modifier = Modifier.height(24.dp))

                DrawerNavItem("INSTAGRAM") {
                    openWindow(url = Constants.INSTAGRAM_URL)
                }
            }
        }
    }
}

@Composable
private fun DrawerNavItem(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = KaszowskaColors.TextDark,
        letterSpacing = 2.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp)
    )
}
