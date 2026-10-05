package org.matias.nocturnatracker.data.local

import android.content.Context
import android.content.SharedPreferences

object AndroidContextProvider {
    var context: Context? = null
}

actual class PlatformLocalStorage actual constructor() {
    private val prefs: SharedPreferences? by lazy {
        AndroidContextProvider.context?.getSharedPreferences("nocturna_prefs", Context.MODE_PRIVATE)
    }

    actual fun getString(key: String): String? {
        return prefs?.getString(key, null)
    }

    actual fun putString(key: String, value: String) {
        prefs?.edit()?.putString(key, value)?.apply()
    }

    actual fun remove(key: String) {
        prefs?.edit()?.remove(key)?.apply()
    }
}
