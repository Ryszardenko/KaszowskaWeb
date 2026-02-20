package com.machmudow.kaszowska.model

data class AppDataContent(
    val aboutSection: AboutSectionData?,
    val groupedServices: GroupedServices?,
    val officeOffer: OfficeOffer?,
    val allPrices: List<PriceCategory>,
    val trainingOffer: TrainingOffer?,
)
