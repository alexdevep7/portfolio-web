package org.artisancode.mywebkotlin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform