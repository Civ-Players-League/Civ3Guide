package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.concurrent.atomicRef
import com.sixbynine.civ3guide.shared.preferences

object CombatDifficultyManager {

  private const val KEY_DIFFICULTY = "key_combat_difficulty"

  private val _difficulty = atomicRef<Difficulty?>(null)

  var difficulty: Difficulty
    get() {
      _difficulty.get()?.let { return it }
      val savedDifficulty = preferences.getInt(KEY_DIFFICULTY) ?: Difficulty.RANDOM.number
      return Difficulty.values()
        .first { it.number == savedDifficulty }
        .also {
          _difficulty.set(it)
        }
    }
    set(value) {
      _difficulty.set(value)
      preferences.putInt(KEY_DIFFICULTY, value.number)
    }
}