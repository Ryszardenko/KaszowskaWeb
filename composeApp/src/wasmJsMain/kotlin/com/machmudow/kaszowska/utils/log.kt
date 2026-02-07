package com.machmudow.kaszowska.utils

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(message) => console.log(message)")
external actual fun logMessage(message: String)
