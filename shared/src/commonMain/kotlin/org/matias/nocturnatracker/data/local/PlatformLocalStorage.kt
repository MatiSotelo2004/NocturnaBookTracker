package org.matias.nocturnatracker.data.local

expect class PlatformLocalStorage() {
    fun getString(key: String): String?
    fun putString(key: String, value: String)
    fun remove(key: String)
}
