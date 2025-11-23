package com.machmudow.kaszowska.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machmudow.kaszowska.components.IlluminatedText
import com.machmudow.kaszowska.sections.model.Section
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.email.openWindow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopNavigation(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit
) {
    val targetColor = if (isScrolled) {
        KaszowskaColors.White.copy(alpha = 0.95f)
    } else {
        KaszowskaColors.SoftBeige
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "topNavBackground",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(
                horizontal = 40.dp,
                vertical = 20.dp,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IlluminatedText(
                text = "MAGDALENA KASZOWSKA",
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
                    url = "https://www.instagram.com/magdalenakaszowska.pmu/",
                )
            }
        }
    }
}
