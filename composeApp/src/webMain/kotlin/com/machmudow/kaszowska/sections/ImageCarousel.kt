package com.machmudow.kaszowska.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageCarousel(
    images: List<DrawableResource>,
    modifier: Modifier = Modifier,
    onImageClick: (DrawableResource) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if (change.positionChange() != Offset.Zero) change.consume()
                        scrollState.dispatchRawDelta(-dragAmount.x)
                    }
                }
                .padding(horizontal = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            images.forEach { image ->
                Image(
                    modifier = Modifier
                        .height(400.dp)
                        .width(360.dp)
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
