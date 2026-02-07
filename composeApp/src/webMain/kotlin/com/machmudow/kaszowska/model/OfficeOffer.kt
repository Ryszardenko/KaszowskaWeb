package com.machmudow.kaszowska.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfficeOffer(
    val meta: OfficeOfferMeta,
    val sections: List<OfficeOfferSection>,
)

@Serializable
data class OfficeOfferMeta(
    val title: String,
    val subtitle: String,
    val brand: String,
)

@Serializable
data class OfficeOfferSection(
    val id: String,
    val title: String,
    @SerialName("intro_text")
    val introText: String,
    @SerialName("additional_info")
    val additionalInfo: String? = null,
    val services: List<OfficeOfferService>,
)

@Serializable
data class OfficeOfferService(
    val name: String,
    val description: String,
    @SerialName("target_area")
    val targetArea: String? = null,
)
