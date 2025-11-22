package com.machmudow.kaszowska.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors

data class Service(
    val title: String,
    val description: String,
    val price: String
)

@Composable
fun ServicesSection() {
    val services = listOf(
        Service(
            "Brwi",
            "Mikropigmentacja brwi metodą włoskową lub cieniowaną. Naturalny efekt dopasowany do kształtu twarzy.",
            "od 800 PLN"
        ),
        Service(
            "Usta",
            "Powiększenie i modelowanie ust. Podkreślenie naturalnego koloru lub zmiana odcienia.",
            "od 900 PLN"
        ),
        Service(
            "Kreski",
            "Kreska górna lub dolna. Delikatne podkreślenie lub wyrazisty efekt.",
            "od 700 PLN"
        ),
        Service(
            "Korekcja",
            "Poprawienie lub odświeżenie istniejącego makijażu permanentnego.",
            "od 500 PLN"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.SoftGray)
            .padding(vertical = 120.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "USŁUGI",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.Gold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Co oferuję",
                fontSize = 42.sp,
                fontWeight = FontWeight.Light,
                color = KaszowskaColors.TextDark,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(80.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                services.forEach { service ->
                    ServiceCard(
                        service = service,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "GALERIA PRAC",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = KaszowskaColors.Gold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            ImageCarousel()
        }
    }
}

@Composable
private fun ServiceCard(
    service: Service,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(KaszowskaColors.White)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = service.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = service.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = service.price,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.Gold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun ImageCarousel() {
    var currentIndex by remember { mutableStateOf(0) }
    val totalImages = 5

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(400.dp)
                        .background(KaszowskaColors.SoftBeige),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "[IMAGE ${(currentIndex + index) % totalImages + 1}]",
                        color = KaszowskaColors.TextLight,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(totalImages) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentIndex) 10.dp else 8.dp)
                        .background(
                            if (index == currentIndex) KaszowskaColors.Gold
                            else KaszowskaColors.Gold.copy(alpha = 0.3f)
                        )
                        .clickable { currentIndex = index }
                )
            }
        }
    }
}

