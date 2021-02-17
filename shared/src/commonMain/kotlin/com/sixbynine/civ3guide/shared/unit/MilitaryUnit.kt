package com.sixbynine.civ3guide.shared.unit

import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.unit.UnitRank.*
import dev.icerock.moko.resources.StringResource

data class MilitaryUnit(
  val rank: UnitRank,
  val type: UnitType,
  val isFortified: Boolean = false,
  val damage: Int = 0,
) {
  val health: Int
    get() = rank.health - damage

  val fullHealth: Int
    get() = rank.health

  val isDead: Boolean
    get() = health == 0
}

enum class UnitRank(val label: StringResource, val health: Int, val retreatFactor: Int) {
  CONSCRIPT(strings.conscript, health = 2, retreatFactor = 34),
  REGULAR(strings.regular, health = 3, retreatFactor = 50),
  VETERAN(strings.veteran, health = 4, retreatFactor = 58),
  ELITE(strings.elite, health = 5, retreatFactor = 66),
}

fun conscript(type: UnitType, isFortified: Boolean = false): MilitaryUnit {
  return MilitaryUnit(CONSCRIPT, type, isFortified)
}

fun regular(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(REGULAR, type, isFortified)

fun veteran(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(VETERAN, type, isFortified)

fun elite(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(ELITE, type, isFortified)