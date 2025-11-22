package com.machmudow.kaszowska.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import com.machmudow.kaszowska.utils.email.SendEmailController
import com.machmudow.kaszowska.utils.email.openWindow

@Composable
fun ContactSection() {
    val controller = remember { SendEmailController() }

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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 60.dp)
            ) {
                Text(
                    text = "KONTAKT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Skontaktuj się ze mną",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light,
                    color = KaszowskaColors.TextDark,
                    letterSpacing = 2.sp,
                    lineHeight = 52.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                ContactInfoItem("Email", "kontakt@kaszowska.pl")
                Spacer(modifier = Modifier.height(24.dp))
                ContactInfoItem("Telefon", "+48 XXX XXX XXX")
                Spacer(modifier = Modifier.height(24.dp))
                ContactInfoItem("Adres", "Warszawa, Polska")

                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = "SOCIAL MEDIA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Instagram: @magdalenakaszowska.pmu",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = KaszowskaColors.Gold,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier
                        .clickable {
                            openWindow(
                                "https://www.instagram.com/magdalenakaszowska.pmu/",
                                "_blank"
                            )
                        }
                        .padding(vertical = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp)
            ) {
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

                Button(
                    onClick = controller::validateAndSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KaszowskaColors.Gold,
                        contentColor = KaszowskaColors.White
                    ),
                    enabled = !controller.isSubmitting
                ) {
                    Text(
                        text = if (controller.isSubmitting) "WYSYŁANIE..." else "WYŚLIJ WIADOMOŚĆ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 2.sp
                    )
                }

                if (controller.submitSuccess) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "✓ Wiadomość została wysłana!",
                        fontSize = 14.sp,
                        color = KaszowskaColors.Gold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactInfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextDark,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun ContactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    multiline: Boolean = false,
    minHeight: androidx.compose.ui.unit.Dp = 56.dp,
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = KaszowskaColors.TextLight,
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
                .background(KaszowskaColors.SoftGray)
                .border(
                    width = 1.dp,
                    color = if (error != null) Color(0xFFD32F2F) else KaszowskaColors.SoftBeige
                )
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
