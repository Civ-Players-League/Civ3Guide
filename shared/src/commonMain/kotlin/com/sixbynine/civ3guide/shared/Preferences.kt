package com.sixbynine.civ3guide.shared

interface Preferences {
  fun putBoolean(key: String, value: Boolean)

  fun getBoolean(key: String): Boolean?

  fun putString(key: String, value: String)

  fun getString(key: String): String?

  fun putInt(key: String, value: Int)

  fun getInt(key: String): Int?
}

expect val preferences: Preferences