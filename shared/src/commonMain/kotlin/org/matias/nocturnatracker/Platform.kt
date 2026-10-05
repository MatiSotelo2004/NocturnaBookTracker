package org.matias.nocturnatracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform