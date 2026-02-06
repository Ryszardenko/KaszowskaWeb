package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Dr Cyj",
        description = "",
        price = "600 ZŁ"
    ),
    PriceItem(
        name = "Stymulacja porostu włosków brwi –",
        description = "Stymulator",
        price = "500 ZŁ+"
    ),
    PriceItem(
        name = "Dr Cyj pakiet",
        description = "4 zabiegów",
        price = "2000 ZŁ"
    )
)

val scalpTissueStimulatorsCategory = PriceCategory(
    name = "Stymulatory tkankowe skóra głowy",
    items = priceList,
)
