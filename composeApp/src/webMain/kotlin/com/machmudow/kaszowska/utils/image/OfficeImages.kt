package com.machmudow.kaszowska.utils.image

import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.office1
import kaszowska.composeapp.generated.resources.office2
import kaszowska.composeapp.generated.resources.office3
import kaszowska.composeapp.generated.resources.office4
import org.jetbrains.compose.resources.DrawableResource

val officeImages: List<DrawableResource> by lazy {
    listOf(
        Res.drawable.office1,
        Res.drawable.office2,
        Res.drawable.office3,
        Res.drawable.office4,
    )
}
