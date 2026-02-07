package com.machmudow.kaszowska.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainingOffer(
    @SerialName("course_meta")
    val courseMeta: CourseMeta,
    @SerialName("hero_section")
    val heroSection: TrainingHeroSection,
    @SerialName("trainer_bio")
    val trainerBio: TrainerBio,
    val curriculum: Curriculum,
    @SerialName("package_includes")
    val packageIncludes: PackageIncludes,
    @SerialName("pricing_options")
    val pricingOptions: PricingOptions,
)

@Serializable
data class CourseMeta(
    val title: String,
    val level: String,
    val method: String,
    val brand: String,
)

@Serializable
data class TrainingHeroSection(
    val headline: String,
    val subheadline: String,
    @SerialName("cta_text")
    val ctaText: String,
)

@Serializable
data class TrainerBio(
    val name: String,
    val role: String,
    val description: String,
    val stats: List<String>,
)

@Serializable
data class Curriculum(
    val title: String,
    val modules: List<String>,
)

@Serializable
data class PackageIncludes(
    val title: String,
    val items: List<PackageItem>,
)

@Serializable
data class PackageItem(
    val item: String,
    val details: String,
)

@Serializable
data class PricingOptions(
    val location: String,
    @SerialName("group_size")
    val groupSize: String,
    val variants: List<PricingVariant>,
)

@Serializable
data class PricingVariant(
    val id: String,
    val name: String,
    val duration: String,
    @SerialName("price_netto")
    val priceNetto: Int,
    val currency: String,
    @SerialName("is_recommended")
    val isRecommended: Boolean = false,
    val description: String? = null,
    val features: List<String>,
)
