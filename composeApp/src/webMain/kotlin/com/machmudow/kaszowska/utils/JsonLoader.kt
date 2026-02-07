package com.machmudow.kaszowska.utils

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response

suspend inline fun <reified T> loadJsonFromResources(fileName: String): T {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    val response = window.fetch("composeResources/kaszowska.composeapp.generated.resources/files/$fileName")
        .await<Response>()
    val jsonString = response.text().await<String>()
    return json.decodeFromString<T>(jsonString)
}
