package com.machmudow.kaszowska.utils.email

import kotlinx.browser.window

// Kotlin/JS implementation using direct browser APIs

actual fun jsEncodeURIComponent(value: String): String {
    return js("encodeURIComponent(value)") as String
}

actual fun openWindow(
    url: String,
    target: String,
) {
    window.open(url, target)
}
