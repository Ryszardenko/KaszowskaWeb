package com.machmudow.kaszowska

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.machmudow.kaszowska.components.*
import com.machmudow.kaszowska.theme.KaszowskaTheme

@Composable
fun App() {
    KaszowskaTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                HeroSection()
                AboutSection()
                ServicesSection()
                ContactSection()
                Footer()
            }

            // Fixed navigation overlay
            TopNavigation()
        }
    }
}