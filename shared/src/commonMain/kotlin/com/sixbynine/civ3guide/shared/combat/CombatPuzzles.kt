package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.preferences

object CombatPuzzles {

  private const val ALWAYS_SHOW_FIRST_TIME_PAGE = true
  private const val KEY_SEEN_FIRST_TIME = "key_seen_first_time"

  fun shouldShowFirstTimePage(): Boolean {
    return ALWAYS_SHOW_FIRST_TIME_PAGE || !(preferences.getBoolean(KEY_SEEN_FIRST_TIME) ?: false)
  }

  fun noteFirstTimePageSeen() {
    preferences.putBoolean(KEY_SEEN_FIRST_TIME, true)
  }
}