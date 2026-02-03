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
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.magda
import org.jetbrains.compose.resources.painterResource

@Composable
fun AboutSection() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val imageAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val imageOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else -100f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    val contentOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 100f,
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
            .padding(vertical = 120.dp, horizontal = 80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            // Left side
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
                    contentScale = ContentScale.Inside,
                )
            }

            // Right side - Content with staggered animations
            Column(
                modifier = Modifier
                    .weight(1f)
                    .graphicsLayer {
                        alpha = contentAlpha
                        translationX = contentOffset
                    }
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
                    text = Constants.FULL_NAME,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextDark,
                    letterSpacing = 2.sp,
                    lineHeight = 52.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = """
                        Nazywam się Magdalena Kaszowska — jestem linergistką, specjalistką terapii blizn i szkoleniowcem.

                        Tworzę naturalny, dopasowany makijaż permanentny oraz naprawiam i odwracam stare pigmentacje, przywracając moim Klientkom pewność siebie.

                        To, co wyróżnia mój gabinet, to zaawansowana praca z bliznami. Zajmuję się bliznami po operacjach, cięciu cesarskim, zabiegach plastycznych, urazach, oparzeniach i trądziku, prowadząc je ku większej elastyczności i estetyce.

                        Jako szkoleniowiec z wiedzą medyczną, tworzę nowe pokolenie specjalistów - ucząc terapii blizn, usuwania pigmentacji i makijażu permanentnego, opierając pracę na zrozumieniu tkanek i realnych efektach. 

                        Najczęściej słyszę od Klientek jedno zdanie: „żałuję, że trafiłam do Pani tak późno”. To dlatego w samym 2025 roku zaufało mi blisko 1000 Klientek, z czego połowie pomogłam w usuwaniu niechcianej pigmentacji i terapii blizn.
                    """.trimIndent(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.TextLight,
                    lineHeight = 28.sp
                )
            }
        }
    }
}
