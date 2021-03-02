package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.Terrain
import com.sixbynine.civ3guide.shared.Terrain.*
import com.sixbynine.civ3guide.shared.combat.CombatResultType.ATTACKER_RETREATS
import com.sixbynine.civ3guide.shared.combat.CombatResultType.DEFENDER_RETREATS
import com.sixbynine.civ3guide.shared.unit.MilitaryUnit
import com.sixbynine.civ3guide.shared.unit.StandardUnitType
import com.sixbynine.civ3guide.shared.unit.UnitRank
import com.sixbynine.civ3guide.shared.unit.UnitRank.*
import kotlin.random.Random

data class Engagement(
  val attacker: MilitaryUnit,
  val defender: MilitaryUnit,
  val terrain: Terrain,
  val acrossRiver: Boolean = false,
  val cityDefenceBonus: Double? = null
)

fun randomEngagement(
  allowUniqueUnits: Boolean,
  allowFastUnits: Boolean,
  allowRetreat: Boolean
): Engagement {
  if (allowRetreat) {
    return randomEngagement(allowUniqueUnits, allowFastUnits)
  }
  while (true) {
    val engagement = randomEngagement(allowUniqueUnits, allowFastUnits)
    val results = CombatCalculator.calculateCombatResults(engagement)
    if (results.results.none { it.isRetreat }) {
      return engagement
    }
  }
}

fun randomEngagement(
  allowUniqueUnits: Boolean,
  allowFastUnits: Boolean
): Engagement {
  val attackerDistribution = StandardUnitType.values().mapNotNull { type ->
    var weight = when {
      type.isUnique -> if (allowUniqueUnits) 1.0 else return@mapNotNull null
      type.isFast -> if (allowFastUnits) 2.5 else return@mapNotNull null
      else -> 4.0
    }
    if (type.attack < type.defence) weight /= 1.5
    if (type.defence < type.attack) weight *= 1.5

    weight to type
  }

  val attackerType = pickRandomFromDistribution(attackerDistribution)
  val attackerRank = pickRandomFromDistribution(rankDistribution)
  val attackerDamage = pickRandomFromDistribution(attackerRank.getDamageDistribution())
  val attacker = MilitaryUnit(
    attackerRank,
    attackerType,
    damage = attackerDamage
  )

  val defenderDistribution = StandardUnitType.values().mapNotNull { type ->
    var weight = when {
      type.isUnique -> if (allowUniqueUnits) 1.0 else return@mapNotNull null
      type.isFast -> if (allowFastUnits) 2.5 else return@mapNotNull null
      else -> 4.0
    }
    if (type.attack < type.defence) weight *= 1.5
    if (type.defence < type.attack) weight /= 1.5

    weight to type
  }

  val defenderType = pickRandomFromDistribution(defenderDistribution)
  val defenderRank = pickRandomFromDistribution(rankDistribution)
  val defenderDamage = pickRandomFromDistribution(defenderRank.getDamageDistribution())
  val defender = MilitaryUnit(
    defenderRank,
    defenderType,
    isFortified = pickRandomFromDistribution(4 to true, 3 to false),
    damage = defenderDamage
  )

  val terrain = pickRandomFromDistribution(terrainDistribution)

  val cityDefenceBonus = if (terrain in listOf(HILLS, GRASSLAND, PLAINS, BONUS_GRASSLAND)) {
    pickRandomFromDistribution(cityBonusDistribution)
  } else {
    null
  }

  return Engagement(
    attacker,
    defender,
    terrain,
    acrossRiver = pickRandomFromDistribution(5 to false, 1 to true),
    cityDefenceBonus = cityDefenceBonus
  )
}

private fun UnitRank.getDamageDistribution(): List<Pair<Int, Int>> {
  val distribution = arrayListOf<Pair<Int, Int>>()
  distribution.add(15 to 0)
  (1 until health).forEach {
    distribution.add(1 to it)
  }
  return distribution
}

private val rankDistribution = listOf(
  4 to REGULAR,
  10 to VETERAN,
  2 to ELITE,
)

private val terrainDistribution = listOf(
  2.0 to GRASSLAND,
  2.0 to PLAINS,
  2.0 to HILLS,
  2.0 to MOUNTAIN,
  2.0 to FOREST,
  1.5 to JUNGLE,
  0.75 to TUNDRA,
  0.75 to DESERT,
  0.75 to MARSH,
  0.25 to VOLCANO,
  0.25 to FLOOD_PLAIN
)

private val cityBonusDistribution = listOf(
  9.0 to null,
  4.0 to 0.0,
  4.0 to 0.5,
  0.25 to 1.0
)

private fun <T> pickRandomFromDistribution(
  first: Pair<Number, T>,
  vararg others: Pair<Number, T>
): T {
  return pickRandomFromDistribution(others.toList() + first)
}

private fun <T> pickRandomFromDistribution(
  distribution: List<Pair<Number, T>>
): T {
  require(distribution.isNotEmpty())
  val sum = distribution.sumByDouble { it.first.toDouble() }
  var roll = Random.nextDouble(sum)
  distribution.forEach {
    roll -= it.first.toDouble()
    if (roll <= 0) {
      return it.second
    }
  }
  return distribution.last().second
}