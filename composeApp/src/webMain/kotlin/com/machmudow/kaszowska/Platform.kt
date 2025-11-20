package com.machmudow.kaszowska

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform