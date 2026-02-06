package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Biolift skóry:",
        description = "Twarz",
        price = "450 ZŁ"
    ),
    PriceItem(
        name = "Biolift skóry:",
        description = "Twarz + szyja",
        price = "550 ZŁ"
    ),
    PriceItem(
        name = "Biolift skóry:",
        description = "Twarz + szyja + dekolt",
        price = "600 ZŁ"
    ),
    PriceItem(
        name = "Biolift dodatek – opracowanie powiek górnej i dolnej",
        description = "",
        price = "+250 ZŁ"
    ),
    PriceItem(
        name = "Pinezkowanie",
        description = "Brwi / zmarszczki / lifting",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Rewitalizacja ust po hialuronidazie",
        description = "Pinezkowanie",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Mezoterapia igłowa Neuvia Hydro Deluxe 2,5 ml",
        description = "Twarz / szyja / dłonie",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Mezoterapia igłowa drenaż limfatyczny Xela Rederm 1.1",
        description = "2 ml twarz",
        price = "650 ZŁ"
    ),
    PriceItem(
        name = "Mezoterapia mikroigłowa + ampułka",
        description = "Nawilżająca / rozświetlająca, rozjaśniająca",
        price = "500 ZŁ"
    )
)

val collagenStimulationAndMesotherapyCategory = PriceCategory(
    name = "Stymulacja koleganu i mezoterapia",
    items = priceList,
)
