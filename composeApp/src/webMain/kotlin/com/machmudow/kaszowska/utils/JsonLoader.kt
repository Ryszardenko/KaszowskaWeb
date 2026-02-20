package com.machmudow.kaszowska.utils

import kotlinx.serialization.json.Json

suspend inline fun <reified T> loadRemoteJson(
    fileName: String,
    remoteUrl: String,
): Result<T> = runCatching {
    if (remoteUrl.isEmpty()) {
        throw IllegalStateException("RemoteUrl is empty. Can not fetch json file $fileName")
    }

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    logMessage("Fetching from remote: $remoteUrl")
    val jsonString = fetchJsonString(remoteUrl)
    json.decodeFromString<T>(jsonString)
}.onFailure {
    logMessage("Remote fetch failed for $fileName, falling back to local: ${it.message}")
}

suspend inline fun <reified T> loadJsonFromResources(
    fileName: String,
): Result<T> = runCatching {
    if (fileName.isEmpty()) {
        throw IllegalStateException("FileName is empty.")
    }

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    val url = "composeResources/kaszowska.composeapp.generated.resources/files/$fileName"
    val jsonString = fetchJsonString(url)
    json.decodeFromString<T>(jsonString)
}.onFailure {
    logMessage("Failed to load json file $fileName - ${it.message}")
}
