package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@Composable
fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh) // Full viewport height
            .background(KaszowskaColors.SoftBeige),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder for hero image background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF9B8577).copy(alpha = 0.3f))
        )

        // Hero Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Permanent Makeup",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "MAGDALENA\nKASZOWSKA",
                fontSize = 64.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White,
                letterSpacing = 8.sp,
                textAlign = TextAlign.Center,
                lineHeight = 72.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Profesjonalny makijaż permanentny",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.White.copy(alpha = 0.9f),
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Extension function for viewport height
private val Int.vh: androidx.compose.ui.unit.Dp
    get() = (this * 10).dp // Approximation for web

