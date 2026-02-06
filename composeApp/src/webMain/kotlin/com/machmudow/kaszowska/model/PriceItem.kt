package com.machmudow.kaszowska.model

data class PriceCategory(
    val name: String,
    val items: List<PriceItem>,
)

data class PriceItem(
    val name: String,
    val description: String = "",
    val price: String,
)
