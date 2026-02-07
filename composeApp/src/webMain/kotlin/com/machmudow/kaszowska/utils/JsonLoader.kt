package com.machmudow.kaszowska.utils

import kotlinx.serialization.json.Json

suspend inline fun <reified T> loadJsonFromResources(fileName: String): T {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    val url = "composeResources/kaszowska.composeapp.generated.resources/files/$fileName"
    val jsonString = fetchJsonString(url)
    return json.decodeFromString<T>(jsonString)
}