package com.machmudow.kaszowska.utils.email

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.definedExternally

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(value) => encodeURIComponent(value)")
external fun jsEncodeURIComponent(value: String): String

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url, target) => window.open(url, target)")
external fun openWindow(
    url: String,
    target: String = definedExternally,
)
