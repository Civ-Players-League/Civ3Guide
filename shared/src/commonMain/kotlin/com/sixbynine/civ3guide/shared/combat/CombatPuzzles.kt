package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.preferences

object CombatPuzzles {

  private const val KEY_SEEN_FIRST_TIME = "key_seen_first_time"

  fun shouldShowFirstTimePage(): Boolean {
    return !(preferences.getBoolean(KEY_SEEN_FIRST_TIME) ?: false)
  }

  fun noteFirstTimePageSeen() {
    preferences.putBoolean(KEY_SEEN_FIRST_TIME, true)
  }
}