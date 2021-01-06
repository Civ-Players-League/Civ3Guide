package com.sixbynine.civ3guide.shared

interface Preferences {
  fun putBoolean(key: String, value: Boolean)

  fun getBoolean(key: String): Boolean?

  fun putString(key: String, value: String)

  fun getString(key: String): String?
}

expect val preferences: Preferences