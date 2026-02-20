package com.machmudow.kaszowska.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupedServices(
    val services: List<Service>
)
