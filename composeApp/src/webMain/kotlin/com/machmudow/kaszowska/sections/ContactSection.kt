package com.machmudow.kaszowska.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.AnimationStateHolder
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.Constants
import com.machmudow.kaszowska.utils.LocalWindowSize
import com.machmudow.kaszowska.utils.horizontalPadding
import com.machmudow.kaszowska.utils.isMobile
import com.machmudow.kaszowska.utils.verticalSectionPadding
import com.machmudow.kaszowska.utils.email.SendEmailController
import com.machmudow.kaszowska.utils.email.openWindow

@Composable
fun ContactSection() {
    val controller = remember { SendEmailController() }
    val windowSize = LocalWindowSize.current

    LaunchedEffect(Unit) {
        AnimationStateHolder.contactSectionVisible = true
    }

    val leftAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.contactSectionVisible) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val leftOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.contactSectionVisible) 0f else -100f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val rightAlpha by animateFloatAsState(
        targetValue = if (AnimationStateHolder.contactSectionVisible) 1f else 0f,
        animationSpec = tween(1000, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    val rightOffset by animateFloatAsState(
        targetValue = if (AnimationStateHolder.contactSectionVisible) 0f else 100f,
        animationSpec = tween(1000, delayMillis = 300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(KaszowskaColors.White)
            .padding(
                vertical = windowSize.verticalSectionPadding,
                horizontal = windowSize.horizontalPadding
            )
    ) {
        if (windowSize.isMobile) {
            // Mobile layout - stacked vertically
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Contact Info
                ContactInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = leftAlpha
                            translationY = leftOffset
                        },
                    isVisible = AnimationStateHolder.contactSectionVisible,
                    isMobile = true
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Contact Form
                ContactForm(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = rightAlpha
                            translationY = rightOffset
                        }
                )
            }
        } else {
            // Desktop layout - side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                // Left side - Contact Info
                ContactInfo(
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            alpha = leftAlpha
                            translationX = leftOffset
                        },
                    isVisible = AnimationStateHolder.contactSectionVisible,
                    isMobile = false
                )

                // Right side - Contact Form
                ContactForm(
                    controller = controller,
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            alpha = rightAlpha
                            translationX = rightOffset
                        }
                )
            }
        }
    }
}

@Composable
private fun ContactInfo(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isMobile: Boolean
) {
    Column(modifier = modifier) {
        Text(
            text = "KONTAKT",
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))

        Text(
            text = "Skontaktuj się ze mną",
            fontSize = if (isMobile) 28.sp else 42.sp,
            fontWeight = FontWeight.Light,
            color = KaszowskaColors.TextDark,
            letterSpacing = 2.sp,
            lineHeight = if (isMobile) 36.sp else 52.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 24.dp else 40.dp))

        ContactInfoItem(
            label = "Email",
            value = Constants.EMAIL,
            isVisible = isVisible,
            delay = 400,
            isMobile = isMobile
        )
        Spacer(modifier = Modifier.height(if (isMobile) 16.dp else 24.dp))
        ContactInfoItem(
            label = "Telefon",
            value = Constants.PHONE_NUMBER,
            isVisible = isVisible,
            delay = 550,
            isMobile = isMobile
        )

        Spacer(modifier = Modifier.height(if (isMobile) 24.dp else 40.dp))

        Text(
            text = "SOCIAL MEDIA",
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.Gold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(if (isMobile) 12.dp else 20.dp))

        Text(
            text = "${Constants.INSTAGRAM}: @${Constants.INSTAGRAM_TAG}",
            fontSize = if (isMobile) 14.sp else 16.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.Gold,
            letterSpacing = 0.5.sp,
            modifier = Modifier
                .clickable {
                    openWindow(
                        Constants.INSTAGRAM_URL,
                        "_blank"
                    )
                }
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun ContactForm(
    controller: SendEmailController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ContactTextField(
            value = controller.name,
            onValueChange = {
                controller.name = it
                controller.nameError = null
            },
            label = "Imię i nazwisko",
            error = controller.nameError
        )

        Spacer(modifier = Modifier.height(24.dp))

        ContactTextField(
            value = controller.email,
            onValueChange = {
                controller.email = it
                controller.emailError = null
            },
            label = "Email",
            error = controller.emailError
        )

        Spacer(modifier = Modifier.height(24.dp))

        ContactTextField(
            value = controller.phone,
            onValueChange = {
                controller.phone = it
                controller.phoneError = null
            },
            label = "Telefon",
            error = controller.phoneError
        )

        Spacer(modifier = Modifier.height(24.dp))

        ContactTextField(
            value = controller.message,
            onValueChange = {
                controller.message = it
                controller.messageError = null
            },
            label = "Wiadomość",
            multiline = true,
            minHeight = 150.dp,
            error = controller.messageError
        )

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedButton(
            onClick = controller::validateAndSubmit,
            enabled = !controller.isSubmitting,
            text = if (controller.isSubmitting) "WYSYŁANIE..." else "WYŚLIJ WIADOMOŚĆ"
        )
    }
}

@Composable
private fun ContactInfoItem(label: String, value: String, isVisible: Boolean, delay: Int, isMobile: Boolean = false) {
    val itemAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = delay, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier.graphicsLayer { alpha = itemAlpha }
    ) {
        Text(
            text = label,
            fontSize = if (isMobile) 11.sp else 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = if (isMobile) 16.sp else 18.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun AnimatedButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String
) {
    var isHovered by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isHovered && enabled) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = KaszowskaColors.Gold,
            contentColor = KaszowskaColors.White,
            disabledContainerColor = KaszowskaColors.SoftGray,
            disabledContentColor = KaszowskaColors.TextLight
        ),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ContactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    multiline: Boolean = false,
    minHeight: Dp = 56.dp,
    error: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = when {
            error != null -> Color(0xFFD32F2F)
            isFocused -> KaszowskaColors.Gold
            isHovered -> KaszowskaColors.Gold.copy(alpha = 0.5f)
            else -> KaszowskaColors.SoftBeige
        },
        animationSpec = tween(300)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isFocused) 4f else 0f,
        animationSpec = tween(300)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = if (isFocused) KaszowskaColors.Gold else KaszowskaColors.TextLight,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = KaszowskaColors.TextDark,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .graphicsLayer {
                    shadowElevation = elevation
                }
                .background(KaszowskaColors.SoftGray.copy(alpha = 0.3f))
                .border(
                    width = if (isFocused) 2.dp else 1.dp,
                    color = borderColor
                )
                .onFocusChanged { isFocused = it.isFocused }
                .onPointerEvent(PointerEventType.Enter) { isHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isHovered = false }
                .padding(16.dp),
            singleLine = !multiline
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = error,
                fontSize = 12.sp,
                color = Color(0xFFD32F2F),
                fontWeight = FontWeight.Normal
            )
        }
    }
}
