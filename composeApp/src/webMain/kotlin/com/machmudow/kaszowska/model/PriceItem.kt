package com.machmudow.kaszowska.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceCategory(
    val name: String,
    val items: List<PriceItem>,
)

@Serializable
data class PriceItem(
    val name: String,
    val description: String = "",
    val price: String,
)
