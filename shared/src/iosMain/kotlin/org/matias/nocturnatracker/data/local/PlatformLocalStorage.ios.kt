package org.matias.nocturnatracker.data.local

import platform.Foundation.NSUserDefaults

actual class PlatformLocalStorage actual constructor() {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun getString(key: String): String? {
        return defaults.stringForKey(key)
    }

    actual fun putString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
    }

    actual fun remove(key: String) {
        defaults.removeObjectForKey(key)
    }
}
