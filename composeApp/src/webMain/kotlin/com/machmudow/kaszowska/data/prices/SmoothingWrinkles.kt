package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "1 okolica",
        description = "",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "2 okolice",
        description = "",
        price = "700 ZŁ"
    ),
    PriceItem(
        name = "3 okolice",
        description = "",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "4 okolice",
        description = "",
        price = "1300 ZŁ"
    ),
    PriceItem(
        name = "Uniesienie brwi",
        description = "Efekt liftingu oka",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Lip flip",
        description = "Efekt wywinięcia ust",
        price = "400 ZŁ"
    ),
)

val smoothingWrinklesCategory = PriceCategory(
    name = "Wygładzanie zmarszczek",
    items = priceList,
)
