package com.machmudow.kaszowska.utils.pdf

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(url) => window.open(url, '_blank')")
external fun openPdfInNewTab(url: String)

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
(containerId, pdfUrl) => {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = '';
        const iframe = document.createElement('iframe');
        iframe.src = pdfUrl;
        iframe.style.width = '100%';
        iframe.style.height = '100%';
        iframe.style.border = 'none';
        iframe.style.borderRadius = '8px';
        container.appendChild(iframe);
    }
}
""")
external fun embedPdfInContainer(containerId: String, pdfUrl: String)

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
(containerId) => {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = '';
    }
}
""")
external fun clearPdfContainer(containerId: String)

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
(containerId) => {
    // Create the container element if it doesn't exist
    let container = document.getElementById(containerId);
    if (!container) {
        container = document.createElement('div');
        container.id = containerId;
        container.style.position = 'fixed';
        container.style.zIndex = '10000';
        document.body.appendChild(container);
    }
    return container.id;
}
""")
external fun createPdfContainer(containerId: String): String

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
(containerId, top, left, width, height) => {
    const container = document.getElementById(containerId);
    if (container) {
        container.style.top = top + 'px';
        container.style.left = left + 'px';
        container.style.width = width + 'px';
        container.style.height = height + 'px';
    }
}
""")
external fun updatePdfContainerPosition(containerId: String, top: Int, left: Int, width: Int, height: Int)

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
(containerId) => {
    const container = document.getElementById(containerId);
    if (container) {
        container.remove();
    }
}
""")
external fun removePdfContainer(containerId: String)

