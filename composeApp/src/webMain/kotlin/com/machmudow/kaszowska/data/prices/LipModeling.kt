package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Rewitalizacja i nawilżenie ust",
        description = "Stymulator",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Rewitalizacja ust po hialuronidazie",
        description = "Pinezkowanie",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Juvederm 0,5 ml",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Juvederm / Restylane 1 ml",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Revolax Fine 1,1 ml",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Revolax Deep 1,1 ml",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Efekt lip flip",
        description = "",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Belotero Balance 1 ml",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Stylage M Bisoft 1 ml",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Modelowanie ust",
        description = "Teoysal Kiss 0,7 ml",
        price = "850 ZŁ"
    )
)

val lipModelingCategory = PriceCategory(
    name = "Modelowanie ust",
    items = priceList,
)
