package com.machmudow.kaszowska.utils.pdf

expect fun openPdfInNewTab(url: String)

expect fun embedPdfInContainer(containerId: String, pdfUrl: String)

expect fun clearPdfContainer(containerId: String)

expect fun createPdfContainer(containerId: String): String

expect fun updatePdfContainerPosition(containerId: String, top: Int, left: Int, width: Int, height: Int)

expect fun removePdfContainer(containerId: String)
