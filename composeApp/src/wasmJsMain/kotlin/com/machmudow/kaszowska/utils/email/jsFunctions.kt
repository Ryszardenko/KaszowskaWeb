package com.machmudow.kaszowska.utils.email

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(value) => encodeURIComponent(value)")
external actual fun jsEncodeURIComponent(value: String): String

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url, target) => window.open(url, target)")
external actual fun openWindow(
    url: String,
    target: String,
)
