package com.machmudow.kaszowska.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IlluminatedText(
            text = Constants.FULL_NAME.uppercase(),
            isScrolled = isScrolled,
        ) {
            onNavigate(Section.HERO)
        }

        Spacer(modifier = Modifier.weight(1f))

        IlluminatedText(
            text = "O MNIE",
            isScrolled = isScrolled,
        ) {
            onNavigate(Section.ABOUT)
        }

        IlluminatedText(
            text = "USŁUGI",
            isScrolled = isScrolled,
        ) {
            onNavigate(Section.SERVICES)
        }

        IlluminatedText(
            text = "KONTAKT",
            isScrolled = isScrolled,
        ) {
            onNavigate(Section.CONTACT)
        }

        IlluminatedText(
            text = "INSTAGRAM",
            isScrolled = isScrolled,
        ) {
            openWindow(
                url = Constants.INSTAGRAM_URL,
            )
        }
    }
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
                DrawerNavItem("STRONA GŁÓWNA") { onNavigate(Section.HERO) }
                DrawerNavItem("O MNIE") { onNavigate(Section.ABOUT) }
                DrawerNavItem("USŁUGI") { onNavigate(Section.SERVICES) }
                DrawerNavItem("KONTAKT") { onNavigate(Section.CONTACT) }

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
