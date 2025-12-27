package com.example.listingstowordconverter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform