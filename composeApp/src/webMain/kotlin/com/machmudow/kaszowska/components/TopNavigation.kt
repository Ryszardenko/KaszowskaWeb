package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@Composable
fun TopNavigation() {
    var isScrolled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isScrolled) KaszowskaColors.White.copy(alpha = 0.95f)
                else Color.Transparent
            )
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo/Brand
            Text(
                text = "MAGDALENA KASZOWSKA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = if (isScrolled) KaszowskaColors.TextDark else KaszowskaColors.White,
                letterSpacing = 2.sp
            )

            // Navigation Menu
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem("O MNIE", isScrolled)
                NavItem("USŁUGI", isScrolled)
                NavItem("KONTAKT", isScrolled)
                NavItem("INSTAGRAM", isScrolled)
            }
        }
    }
}

@Composable
private fun NavItem(text: String, isScrolled: Boolean) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        color = if (isScrolled) KaszowskaColors.TextDark else KaszowskaColors.White,
        letterSpacing = 1.5.sp,
        modifier = Modifier
            .clickable { /* TODO: Scroll to section */ }
            .padding(vertical = 8.dp)
    )
}

