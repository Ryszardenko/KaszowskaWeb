package com.machmudow.kaszowska.utils.image

import kaszowska.composeapp.generated.resources.Res
import kaszowska.composeapp.generated.resources.work1
import kaszowska.composeapp.generated.resources.work2
import kaszowska.composeapp.generated.resources.work3
import kaszowska.composeapp.generated.resources.work4
import kaszowska.composeapp.generated.resources.work5
import kaszowska.composeapp.generated.resources.work6
import org.jetbrains.compose.resources.DrawableResource

val workImages: List<DrawableResource> by lazy {
    listOf(
        Res.drawable.work1,
        Res.drawable.work2,
        Res.drawable.work3,
        Res.drawable.work4,
        Res.drawable.work5,
        Res.drawable.work6,
    )
}
