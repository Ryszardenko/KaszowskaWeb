package com.machmudow.kaszowska

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.machmudow.kaszowska.components.*
import com.machmudow.kaszowska.theme.KaszowskaTheme
import kotlinx.coroutines.launch

enum class Section(val index: Int) {
    HERO(0),
    ABOUT(1),
    SERVICES(2),
    CONTACT(3),
    FOOTER(4),
}

@Composable
fun App() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isScrolled =
        listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 50

    val scrollToSection: (Section) -> Unit = { section ->
        coroutineScope.launch {
            listState.animateScrollToItem(section.index)
        }
    }

    KaszowskaTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item { HeroSection() }
                item { AboutSection() }
                item { ServicesSection() }
                item { ContactSection() }
                item { Footer() }
            }

            // Fixed navigation overlay
            TopNavigation(
                isScrolled = isScrolled,
                onNavigate = scrollToSection,
            )
        }
    }
}