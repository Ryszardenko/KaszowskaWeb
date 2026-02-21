package com.machmudow.kaszowska.utils

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response

actual suspend fun fetchJsonString(url: String): String {
    val cacheBusterUrl = if (url.contains("?")) "$url&t=${kotlin.js.Date.now()}" else "$url?t=${kotlin.js.Date.now()}"
    val response: Response = window.fetch(cacheBusterUrl).await()
    return response.text().await()
}
