package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import kotlinx.browser.window

@Composable
fun Footer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.DarkBrown)
            .padding(vertical = 60.dp, horizontal = 40.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MAGDALENA KASZOWSKA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Permanent Makeup Artist",
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.7f),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                FooterLink("Instagram") {
                    window.open("https://www.instagram.com/magdalenakaszowska.pmu/", "_blank")
                }
                FooterLink("Kontakt") { /* TODO: Scroll to contact */ }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "© 2024 Magdalena Kaszowska. All rights reserved.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.5f),
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FooterLink(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = KaszowskaColors.White.copy(alpha = 0.8f),
        letterSpacing = 1.5.sp,
    )
}

