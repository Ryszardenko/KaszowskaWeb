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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machmudow.kaszowska.theme.KaszowskaColors
import kotlinx.browser.window

@Composable
fun ContactSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

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
            // Left side - Contact info
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
                            window.open("https://www.instagram.com/magdalenakaszowska.pmu/", "_blank")
                        }
                        .padding(vertical = 4.dp)
                )
            }

            // Right side - Contact form
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 60.dp)
            ) {
                ContactTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Imię i nazwisko"
                )

                Spacer(modifier = Modifier.height(24.dp))

                ContactTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email"
                )

                Spacer(modifier = Modifier.height(24.dp))

                ContactTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Telefon"
                )

                Spacer(modifier = Modifier.height(24.dp))

                ContactTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = "Wiadomość",
                    multiline = true,
                    minHeight = 150.dp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { /* TODO: Handle form submission */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KaszowskaColors.Gold,
                        contentColor = KaszowskaColors.White
                    )
                ) {
                    Text(
                        text = "WYŚLIJ WIADOMOŚĆ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 2.sp
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
    minHeight: androidx.compose.ui.unit.Dp = 56.dp
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
                .border(1.dp, KaszowskaColors.SoftBeige)
                .padding(16.dp),
            singleLine = !multiline
        )
    }
}

