package com.machmudow.kaszowska.utils.email

expect fun jsEncodeURIComponent(value: String): String

expect fun openWindow(
    url: String,
    target: String = "_self",
)
