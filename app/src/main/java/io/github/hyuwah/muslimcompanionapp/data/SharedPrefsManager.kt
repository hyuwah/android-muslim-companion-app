package io.github.hyuwah.muslimcompanionapp.data

import android.content.Context
import androidx.core.content.edit

// Singleton SharedPreferences Manager
class SharedPrefsManager constructor(context: Context) {

    private val SETTINGS_NAME = "mca_prefs"
    private val prefs = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    enum class Key {
        EDITION_KEY, CURRENT_AYAH_ID_INT
    }

    fun put(key: Key, value: String) {
        prefs.edit {
            putString(key.name, value)
        }
    }

    fun put(key: Key, value: Int) {
        prefs.edit {
            putInt(key.name, value)
        }
    }

    fun put(key: Key, value: Boolean) {
        prefs.edit {
            putBoolean(key.name, value)
        }
    }

    fun put(key: Key, value: Float) {
        prefs.edit {
            putFloat(key.name, value)
        }
    }

    fun put(key: Key, value: Long) {
        prefs.edit {
            putLong(key.name, value)
        }
    }

    fun getString(key: Key, defaultValue: String = ""): String {
        return prefs.getString(key.name, defaultValue).orEmpty()
    }

    fun getInt(key: Key, defaultValue: Int = 0): Int {
        return prefs.getInt(key.name, defaultValue)
    }

    fun getLong(key: Key, defaultValue: Long = 0): Long {
        return prefs.getLong(key.name, defaultValue)
    }

    fun getFloat(key: Key, defaultValue: Float = 0f): Float {
        return prefs.getFloat(key.name, defaultValue)
    }

    fun getBoolean(key: Key, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key.name, defaultValue)
    }

    fun remove(vararg keys: Key) {
        for (key in keys) {
            prefs.edit {
                remove(key)
            }
        }
    }

    fun clear() {
        prefs.edit {
            clear()
        }
    }

}