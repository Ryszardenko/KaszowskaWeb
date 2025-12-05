package com.machmudow.kaszowska.utils.pdf

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url) => window.open(url, '_blank')")
external fun openPdfInNewTab(url: String)
