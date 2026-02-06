package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Dives Med. – Trehfill",
        description = "",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "Lipoliza iniekcyjna",
        description = "Stymulator lipolityczny",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Sunekos 1200",
        description = "",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Xela Rederm 1.1%",
        description = "",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Xela Rederm 1.8%",
        description = "",
        price = "900 ZŁ"
    ),
    PriceItem(
        name = "Xela Rederm 2.2%",
        description = "(Przebarwienia)",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "Profhilo 2 ml",
        description = "",
        price = "1100 ZŁ"
    ),
    PriceItem(
        name = "Sisthaema 2 ml",
        description = "",
        price = "1000 ZŁ"
    ),
    PriceItem(
        name = "Karisma Rh",
        description = "Kolagen 2 ml",
        price = "1100 ZŁ"
    ),
    PriceItem(
        name = "Sunekos 200",
        description = "",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Ejal40 2 ml",
        description = "",
        price = "800 ZŁ"
    )
)

val facialTissueStimulatorsCategory = PriceCategory(
    name = "Stymulatory tkankowe twarz",
    items = priceList,
)
