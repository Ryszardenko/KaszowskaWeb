package com.machmudow.kaszowska.utils.pdf

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLIFrameElement

// Kotlin/JS implementation using direct DOM manipulation

actual fun openPdfInNewTab(url: String) {
    window.open(url, "_blank")
}

actual fun embedPdfInContainer(containerId: String, pdfUrl: String) {
    val container = document.getElementById(containerId) as? HTMLDivElement ?: return
    container.innerHTML = ""

    val iframe = document.createElement("iframe") as HTMLIFrameElement
    iframe.src = pdfUrl
    iframe.style.width = "100%"
    iframe.style.height = "100%"
    iframe.style.border = "none"
    iframe.style.borderRadius = "8px"
    container.appendChild(iframe)
}

actual fun clearPdfContainer(containerId: String) {
    val container = document.getElementById(containerId) as? HTMLDivElement
    container?.let {
        it.innerHTML = ""
    }
}

actual fun createPdfContainer(containerId: String): String {
    var container = document.getElementById(containerId) as? HTMLDivElement
    if (container == null) {
        container = document.createElement("div") as HTMLDivElement
        container.id = containerId
        container.style.position = "fixed"
        container.style.zIndex = "10000"
        document.body?.appendChild(container)
    }
    return container.id
}

actual fun updatePdfContainerPosition(containerId: String, top: Int, left: Int, width: Int, height: Int) {
    val container = document.getElementById(containerId) as? HTMLDivElement
    container?.let {
        it.style.top = "${top}px"
        it.style.left = "${left}px"
        it.style.width = "${width}px"
        it.style.height = "${height}px"
    }
}

actual fun removePdfContainer(containerId: String) {
    val container = document.getElementById(containerId) as? HTMLDivElement
    container?.remove()
}
