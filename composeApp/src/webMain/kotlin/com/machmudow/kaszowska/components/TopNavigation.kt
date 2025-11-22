package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.model.Section
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.email.openWindow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopNavigation(
    isScrolled: Boolean,
    onNavigate: (Section) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isScrolled) {
                    KaszowskaColors.White.copy(alpha = 0.95f)
                } else {
                    Color.Transparent
                }
            )
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
            NavItem(
                text = "MAGDALENA KASZOWSKA",
                isScrolled = isScrolled,
            ) {
                onNavigate(Section.HERO)
            }

            Spacer(modifier = Modifier.weight(1f))

            NavItem(
                text = "O MNIE",
                isScrolled = isScrolled,
            ) {
                onNavigate(Section.ABOUT)
            }

            NavItem(
                text = "USŁUGI",
                isScrolled = isScrolled,
            ) {
                onNavigate(Section.SERVICES)
            }

            NavItem(
                text = "KONTAKT",
                isScrolled = isScrolled,
            ) {
                onNavigate(Section.CONTACT)
            }

            NavItem(
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NavItem(
    text: String,
    fontSize: TextUnit = 13.sp,
    isScrolled: Boolean, onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    Text(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(vertical = 8.dp)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        color = when {
            isHovered -> KaszowskaColors.Gold
            isScrolled -> KaszowskaColors.TextDark
            else -> KaszowskaColors.White
        },
        letterSpacing = 1.5.sp,
    )
}
