package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Usuwanie laserowe",
        description = "(Brwi / usta / piegi)",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Ocieplanie laserowe",
        description = "Brwi",
        price = "200 ZŁ"
    ),
    PriceItem(
        name = "Neutralizacja laserowa",
        description = "Ściągnięcie czerwonej poświaty z brwi",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Usuwanie laserowe kreski permanentnej",
        description = "(Kreska na powiece)",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Remover",
        description = "(Kwasowy / zasadowy / solny)",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Biopeeling",
        description = "(Rozjaśnienie + rewitalizacja)",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Usuwanie pigmentu uwięzionego w bliźnie",
        description = "Terapia blizny",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Ampułka",
        description = "Indywidualnie dopasowana regeneracja",
        price = "150 ZŁ"
    )
)

val permanentMakeUpRemovalCategory = PriceCategory(
    name = "Usuwanie makijażu permanentego",
    items = priceList,
)
