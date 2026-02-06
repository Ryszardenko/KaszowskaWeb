package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Pojedyncza blizna do 5 cm",
        description = "",
        price = "350 ZŁ"
    ),
    PriceItem(
        name = "Pojedyncza blizna 5-10 cm",
        description = "",
        price = "400+ ZŁ"
    ),
    PriceItem(
        name = "Pojedyncza blizna powyżej 10 cm",
        description = "",
        price = "500+ ZŁ"
    ),
    PriceItem(
        name = "Blizny na brwiach",
        description = "",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Rewitalizacja brwi po usuwaniu",
        description = "Removerem / laserem",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Blizna po cesarskim cięciu",
        description = "",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Blizny potrądzikowe",
        description = "",
        price = "400 ZŁ"
    ),
    PriceItem(
        name = "Blizna po rozszczepie",
        description = "",
        price = "350 ZŁ"
    ),
    PriceItem(
        name = "Blizny na powiekach",
        description = "(Po blepharoplastyce)",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Rozstępy",
        description = "Sesja 45 minut",
        price = "400+ ZŁ"
    ),
    PriceItem(
        name = "Redukcja przebarwień",
        description = "",
        price = "350+ ZŁ"
    ),
    PriceItem(
        name = "Suche igłowanie",
        description = "Rozluźnianie blizn",
        price = "300+ ZŁ"
    ),
    PriceItem(
        name = "Tropokolagen",
        description = "Redukcja blizn",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Stymulator tkankowy na bliznę",
        description = "",
        price = "WYCENA INDYWIDUALNA"
    )
)

val scarTherapyCategory = PriceCategory(
    name = "Terapia blizn - Skin needling Mikropunktura",
    items = priceList,
)