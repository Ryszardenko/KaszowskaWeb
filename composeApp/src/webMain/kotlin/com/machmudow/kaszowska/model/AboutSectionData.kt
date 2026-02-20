package com.machmudow.kaszowska.model

import kotlinx.serialization.Serializable

@Serializable
data class AboutSectionData(
    val title: String,
    val name: String,
    val paragraphs: List<String>
)
