package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Usuwanie laserowe tatuażu 2x2 cm",
        description = "",
        price = "200 ZŁ"
    ),
    PriceItem(
        name = "Usuwanie laserowe tatuażu 5x5 cm",
        description = "",
        price = "340 ZŁ"
    ),
    PriceItem(
        name = "Usuwanie laserowe tatuażu 10x10 cm",
        description = "",
        price = "450 ZŁ"
    ),
    PriceItem(
        name = "Laserowe usuwanie tatuażu powyżej 10 cm",
        description = "",
        price = "550 + ZŁ"
    ),
    PriceItem(
        name = "Pakiet 3 zabiegów",
        description = "Usuwania tatuażu 2x2",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Pakiet 3 zabiegów",
        description = "Usuwania tatuażu 5x5",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Pakiet 3 zabiegów",
        description = "Usuwania tatuażu 10x10",
        price = "1150 ZŁ"
    ),
    PriceItem(
        name = "Pakiet 3 zabiegów",
        description = "Usuwania tatuażu 10cm+",
        price = "1400+ ZŁ"
    ),
    PriceItem(
        name = "Ampułka",
        description = "Indywidualnie dopasowana regeneracja",
        price = "150 ZŁ"
    )
)

val tattoosRemovalCategory = PriceCategory(
    name = "Usuwanie tatuażu",
    items = priceList,
)
