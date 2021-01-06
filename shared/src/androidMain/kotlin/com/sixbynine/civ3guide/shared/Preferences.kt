package com.sixbynine.civ3guide.shared

import android.content.Context
import android.content.SharedPreferences

object AndroidPreferences: Preferences {

  private val prefs: SharedPreferences
    get() = Civ3GuideApplication.instance.getSharedPreferences("civ3guide", Context.MODE_PRIVATE)

  override fun putBoolean(key: String, value: Boolean) {
    prefs.edit().putBoolean(key, value).apply()
  }

  override fun getBoolean(key: String): Boolean? {
    return if (prefs.contains(key)) {
      prefs.getBoolean(key, false)
    } else {
      null
    }
  }

  override fun putString(key: String, value: String) {
    prefs.edit().putString(key, value).apply()
  }

  override fun getString(key: String): String? {
    return prefs.getString(key, null)
  }
}

actual val preferences: Preferences = AndroidPreferences