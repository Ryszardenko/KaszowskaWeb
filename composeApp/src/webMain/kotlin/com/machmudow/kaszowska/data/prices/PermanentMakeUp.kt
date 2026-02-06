package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Brwi Ombre",
        price = "1200 zł",
    ),
    PriceItem(
        name = "Brwi Włoskowe",
        price = "1400 zł",
    ),
    PriceItem(
        name = "Brwi Combo",
        description = "Włos + Cień",
        price = "1400 zł",
    ),
    PriceItem(
        name = "Usta Efekt Full Lips",
        price = "1200 zł",
    ),
    PriceItem(
        name = "Usta Efekt Aquarelle",
        price = "1200 zł",
    ),
    PriceItem(
        name = "Powieki Kreska Zagęszczająca",
        price = "800 zł",
    ),
    PriceItem(
        name = "Powieki Kreska Dekoracyjna",
        price = "1000 zł",
    ),
    PriceItem(
        name = "Piegi Permanentne",
        description = "Full Face",
        price = "900 zł",
    ),
    PriceItem(
        name = "Piegi Permanentne",
        description = "Soft",
        price = "700 zł",
    ),
    PriceItem(
        name = "Piegi Permanentne",
        description = "Zagęszczenie (kolejny zabieg w serii)",
        price = "400 zł",
    ),
    PriceItem(
        name = "Dopigmentowanie",
        description = "do 3 miesięcy",
        price = "300 zł",
    ),
    PriceItem(
        name = "Odświeżenie Efektu",
        description = "do 1 roku",
        price = "600 zł",
    ),
    PriceItem(
        name = "Odświeżenie Efektu",
        description = "do 1,5 roku",
        price = "800 zł",
    ),
    PriceItem(
        name = "Odświeżenie Efektu",
        description = "powyżej 1,5 roku",
        price = "1000 zł",
    ),
)

val permanentMakeUpPriceCategory = PriceCategory(
    name = "Makijaż Permanentny",
    items = priceList,
)
