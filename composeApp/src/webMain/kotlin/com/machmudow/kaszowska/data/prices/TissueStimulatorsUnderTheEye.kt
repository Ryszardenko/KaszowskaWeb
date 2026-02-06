package com.machmudow.kaszowska.data.prices

import com.machmudow.kaszowska.model.PriceCategory
import com.machmudow.kaszowska.model.PriceItem

private val priceList = listOf(
    PriceItem(
        name = "Sunekos 200",
        description = "1 ml",
        price = "700 ZŁ"
    ),
    PriceItem(
        name = "Xela Rederm 1.1%",
        description = "2 ml",
        price = "700 ZŁ"
    ),
    PriceItem(
        name = "Pluryal Silk",
        description = "2 ml",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Hyalual Electri 0,55%",
        description = "1,5 ml",
        price = "600 ZŁ"
    ),
    PriceItem(
        name = "Lumi Eyes",
        description = "1 ml",
        price = "500 ZŁ"
    ),
    PriceItem(
        name = "Nucleofill Soft Eyes Plus",
        description = "2 ml",
        price = "800 ZŁ"
    ),
    PriceItem(
        name = "Jalupro Young Eye",
        description = "1 ml",
        price = "700 ZŁ"
    ),
)

val tissueStimulatorsUnderTheEyeCategory = PriceCategory(
    name = "Stymulatory tkankowe pod oko",
    items = priceList,
)
