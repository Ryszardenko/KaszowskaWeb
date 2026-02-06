package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Laser tulowy",
        description = "Twarz",
        price = "1500 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2",
        description = "Twarz",
        price = "1500 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2 blizny potrądzikowe",
        description = "",
        price = "1200 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2 blizna po cesarskim cięciu",
        description = "",
        price = "600 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2 blizny pod piersiami",
        description = "",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2 blizny na powiekach",
        description = "(Po blepharoplastyce)",
        price = "600 ZŁ"
    ),
    PriceItem(
        name = "Laser frakcyjny CO2 blizny pozostałe",
        description = "",
        price = "WYCENA INDYWIDUALNA"
    ),
    PriceItem(
        name = "Ampułka",
        description = "Indywidualnie dopasowana regeneracja",
        price = "150 ZŁ"
    )
)

val laserTherapyCategory = PriceCategory(
    name = "Laseroterapia",
    items = priceList,
)