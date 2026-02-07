package com.machmudow.kaszowska

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AnimationStateHolder {
    var aboutSectionVisible by mutableStateOf(false)
    var servicesSectionVisible by mutableStateOf(false)
    var pricesSectionVisible by mutableStateOf(false)
    var contactSectionVisible by mutableStateOf(false)
    var footerVisible by mutableStateOf(false)
}
