package com.machmudow.kaszowska.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageCarousel(
    images: List<DrawableResource>,
    modifier: Modifier = Modifier,
    onImageClick: (DrawableResource) -> Unit,
) {
    val scrollState = rememberScrollState()
    val windowSize = LocalWindowSize.current

    val imageHeight = if (windowSize.isMobile) 280.dp else 400.dp
    val imageWidth = if (windowSize.isMobile) 240.dp else 360.dp
    val imageSpacing = if (windowSize.isMobile) 12.dp else 20.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = windowSize.horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(imageSpacing)
        ) {
            images.forEach { image ->
                Image(
                    modifier = Modifier
                        .height(imageHeight)
                        .width(imageWidth)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onImageClick(image) },
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
