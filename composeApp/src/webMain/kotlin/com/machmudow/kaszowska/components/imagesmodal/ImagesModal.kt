package com.machmudow.kaszowska.components.imagesmodal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.machmudow.kaszowska.theme.KaszowskaColors
import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.compose_multiplatform
import kaszowska.composeapp.generated.resources.ic_close
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImagesModal(
    images: List<DrawableResource>,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onDismiss() },
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 768.dp),
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 16.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(images) { image ->
                PriceListImage(image = image)
            }
        }

        CloseButton(
            onClick = onDismiss,
        )
    }
}

@Composable
private fun PriceListImage(
    image: DrawableResource,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F8F8))
    ) {
        // Image
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.CloseButton(onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }

    IconButton(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(24.dp)
            .size(48.dp)
            .clip(CircleShape)
            .graphicsLayer {
                scaleX = if (isHovered) 1.2f else 1f
                scaleY = if (isHovered) 1.2f else 1f
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_close),
            contentDescription = null,
            tint = if (isHovered) {
                KaszowskaColors.Gold
            } else {
                Color.White.copy(alpha = 0.9f)
            },
        )
    }
}
