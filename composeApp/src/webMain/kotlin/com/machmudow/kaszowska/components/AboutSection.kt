package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

@Composable
fun AboutSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.White)
            .padding(vertical = 120.dp, horizontal = 40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            // Left side - Image placeholder
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(600.dp)
                    .padding(end = 60.dp)
                    .background(KaszowskaColors.SoftBeige)
            ) {
                // Placeholder for image
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "[PHOTO]",
                        color = KaszowskaColors.TextLight,
                        fontSize = 14.sp
                    )
                }
            }

            // Right side - Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "O MNIE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Magdalena Kaszowska",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextDark,
                    letterSpacing = 2.sp,
                    lineHeight = 52.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Jestem specjalistką makijażu permanentnego z wieloletnim doświadczeniem. " +
                            "Moją pasją jest podkreślanie naturalnego piękna i pomaganie kobietom w budowaniu pewności siebie.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Każdy zabieg traktuję indywidualnie, dostosowując technikę i kształt " +
                            "do Twoich potrzeb i oczekiwań. Pracuję tylko na najwyższej jakości produktach, " +
                            "które zapewniają bezpieczeństwo i trwałe efekty.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Credentials or highlights
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HighlightItem("Certyfikowany specjalista")
                    HighlightItem("Ponad X lat doświadczenia")
                    HighlightItem("Setki zadowolonych klientek")
                }
            }
        }
    }
}

@Composable
private fun HighlightItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(KaszowskaColors.Gold)
        )

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )
    }
}

