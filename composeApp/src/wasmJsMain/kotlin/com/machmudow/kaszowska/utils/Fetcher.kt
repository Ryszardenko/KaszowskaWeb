package com.machmudow.kaszowska.utils

import kotlinx.coroutines.await
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsString
import kotlin.js.Promise

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url) => window.fetch(url).then(response => response.text())")
private external fun fetchText(url: String): Promise<JsString>

actual suspend fun fetchJsonString(url: String): String {
    val jsString: JsString = fetchText(url).await()
    return jsString.toString()
}
