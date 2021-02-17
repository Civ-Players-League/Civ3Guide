package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.Terrain
import com.sixbynine.civ3guide.shared.Tile
import com.sixbynine.civ3guide.shared.combat.CombatResultType.*
import com.sixbynine.civ3guide.shared.unit.MilitaryUnit

/** Result of a particular instance/roll of combat. */
enum class CombatResultType {
  DEFENDER_WINS,
  ATTACKER_WINS,
  DEFENDER_RETREATS,
  ATTACKER_RETREATS,
}

data class CombatResult(
  val probability: Double,
  val attacker: MilitaryUnit,
  val defender: MilitaryUnit
) {
  val isRetreat: Boolean
    get() = attacker.health > 0 && defender.health > 0
}

data class CombatResults(val results: List<CombatResult>) {

  /**
   * Attacker favorability is a measure of how favorable the combat is for the attacker. This is
   * more or less a measure of expected value of shields lost for defender vs attacker, or you could
   * think of this as "return on investment" for risking shields. 1 implies a fair exchange. < 1 is
   * good for the defender. > 1 is good for the attacker.
   *
   * As an example, a favorability score of 0.5 would mean that you expect that for every shield of
   * unit cost you lose in the attack, the defender loses half (not good).
   *
   * In cases where the attacker retreats, this is considered as a win proportional to damage dealt.
   * So, a retreat where you inflict two damage on a four health defender counts like half a win.
   *
   * Defender retreating is seen as a win proportional to damage dealt. Causing a four-health unit
   * to retreat (with one health) is seen as 3/4 of a win.
   */
  val attackerFavorability: Double
    get() {
      val pWin = p(ATTACKER_WINS)
      val pLoss = p(DEFENDER_WINS)
      val winLossRatio = pWin / pLoss
      val costRatio =
        results.first().attacker.type.cost.toDouble() / results.first().defender.type.cost

      if (p(ATTACKER_RETREATS) == 0.0 && p(DEFENDER_RETREATS) == 0.0) {
        return winLossRatio / costRatio
      }

      val isAttackerRetreat = results.any { it.attacker.type.isFast }
      val originalAttackerHealth = results.maxByOrNull { it.attacker.health }!!.attacker.health
      val originalDefenderHealth = results.maxByOrNull { it.defender.health }!!.defender.health

      var pRetreat = 0.0
      var retreatUnitDamageSum = 0.0
      results.filter { it.isRetreat }.forEach {
        val retreaterDamage = if (isAttackerRetreat) {
          originalDefenderHealth - it.defender.health
        } else {
          originalAttackerHealth - it.attacker.health
        }
        retreatUnitDamageSum += retreaterDamage / it.probability
        pRetreat += it.probability
      }

      val averageRetreatDamageDealt = retreatUnitDamageSum * pRetreat

      return (p(ATTACKER_WINS) + p(DEFENDER_WINS)) * winLossRatio / costRatio +
          (averageRetreatDamageDealt / originalDefenderHealth) * pRetreat
    }

  fun flatten(): CombatResults {
    val newResultMap = mutableMapOf<Pair<MilitaryUnit, MilitaryUnit>, Double>()
    results.forEach {
      val key = it.attacker to it.defender
      newResultMap[key] = (newResultMap[key] ?: 0.0) + it.probability
    }
    return CombatResults(newResultMap.map { (key, probability) ->
      val (attacker, defender) = key
      CombatResult(probability = probability, attacker = attacker, defender = defender)
    })
  }

  fun p(result: CombatResultType): Double {
    val filter: (CombatResult) -> Boolean = when (result) {
      DEFENDER_WINS -> {
        { it.attacker.isDead }
      }
      ATTACKER_WINS -> {
        { it.defender.isDead }
      }
      DEFENDER_RETREATS -> {
        { it.defender.health == 1 && it.attacker.health > 1 }
      }
      ATTACKER_RETREATS -> {
        { it.attacker.health == 1 && it.defender.health > 1 }
      }
    }
    return results.filter(filter).sumByDouble { it.probability }
  }
}

data class IntermediateCombatResults(val results: List<IntermediateCombatResult>) {
  fun p(result: CombatResultType): Double {
    return results.firstOrNull { it.result == result }?.probability ?: 0.0
  }
}

data class IntermediateCombatResult(
  val probability: Double,
  val result: CombatResultType,
)

object CombatCalculator {

  fun calculateCombatResults(
    engagement: Engagement
  ): CombatResults {
    return calculateCombatResults(
      engagement,
      engagement.attacker,
      engagement.defender,
      1.0
    ).flatten()
  }

  fun calculateCombatResults(
    engagement: Engagement,
    attacker: MilitaryUnit,
    defender: MilitaryUnit,
  ): CombatResults {
    return calculateCombatResults(
      engagement,
      attacker,
      defender,
      1.0
    ).flatten()
  }

  private fun calculateCombatResults(
    engagement: Engagement,
    attacker: MilitaryUnit,
    defender: MilitaryUnit,
    probability: Double
  ): CombatResults {
    val results = arrayListOf<CombatResult>()

    fun calculateConditionalCombatResults(
      attacker: MilitaryUnit,
      defender: MilitaryUnit,
      probability: Double
    ): List<CombatResult> {
      return calculateCombatResults(
        engagement,
        attacker,
        defender,
        probability
      ).results
    }

    val intermediateResults =
      calculateIntermediateCombatResults(
        engagement,
        attacker,
        defender,
        probability
      )

    val pAttackerWins = intermediateResults.p(ATTACKER_WINS)
    val defenderAfterLoss = defender.copy(damage = defender.damage + 1)
    if (defenderAfterLoss.isDead) {
      results.add(CombatResult(pAttackerWins, attacker, defenderAfterLoss))
    } else {
      results.addAll(calculateConditionalCombatResults(attacker, defenderAfterLoss, pAttackerWins))
    }

    val pDefenderWins = intermediateResults.p(DEFENDER_WINS)
    val attackerAfterLoss = attacker.copy(damage = attacker.damage + 1)
    if (attackerAfterLoss.isDead) {
      results.add(CombatResult(pDefenderWins, attackerAfterLoss, defender))
    } else {
      results.addAll(calculateConditionalCombatResults(attackerAfterLoss, defender, pDefenderWins))
    }

    val pAttackerRetreats = intermediateResults.p(ATTACKER_RETREATS)
    if (pAttackerRetreats > 0) {
      results.add(CombatResult(pAttackerRetreats, attacker, defender))
    }

    val pDefenderRetreats = intermediateResults.p(DEFENDER_RETREATS)
    if (pDefenderRetreats > 0) {
      results.add(CombatResult(pDefenderRetreats, attacker, defender))
    }

    return CombatResults(results)
  }

  fun calculateIntermediateCombatResults(
    engagement: Engagement,
    attacker: MilitaryUnit,
    defender: MilitaryUnit,
    probability: Double = 1.0
  ): IntermediateCombatResults {
    val terrain = engagement.terrain
    val acrossRiver = engagement.acrossRiver
    val cityDefenceBonus = engagement.cityDefenceBonus
    var remainingProbability = probability
    val results = arrayListOf<IntermediateCombatResult>()

    if (engagement.attacker.health > 1 && attacker.canRetreatFrom(defender)) {
      // Probability == 1 means this is the first part of combat. You can't retreat if you start
      // with 1 health.
      val pAttackerRetreats = remainingProbability * attacker.retreatProbabilityFrom(defender)
      results.add(
        IntermediateCombatResult(probability = pAttackerRetreats, result = ATTACKER_RETREATS)
      )
      remainingProbability -= pAttackerRetreats
    }

    if (engagement.defender.health > 1 &&
      cityDefenceBonus == null &&
      defender.canRetreatFrom(attacker)) {
      // Defenders can't retreat when defending cities also.
      val pDefenderRetreats = remainingProbability * defender.retreatProbabilityFrom(attacker)
      results.add(
        IntermediateCombatResult(probability = pDefenderRetreats, result = DEFENDER_RETREATS)
      )
      remainingProbability -= pDefenderRetreats
    }

    val attackerStrength = attacker.type.attack

    var defenceMultiplier = 1.0
    if (defender.isFortified) defenceMultiplier += 0.25
    if (acrossRiver) defenceMultiplier += 0.25
    defenceMultiplier += terrain.getOutput().defenceBonus
    defenceMultiplier += cityDefenceBonus ?: 0.0
    val defenderStrength = defender.type.defence * defenceMultiplier

    val pAttackerWins =
      remainingProbability * attackerStrength / (attackerStrength + defenderStrength)
    val pDefenderWins = remainingProbability - pAttackerWins

    results.add(IntermediateCombatResult(probability = pAttackerWins, result = ATTACKER_WINS))
    results.add(IntermediateCombatResult(probability = pDefenderWins, result = DEFENDER_WINS))

    return IntermediateCombatResults(results)
  }

  private fun MilitaryUnit.canRetreatFrom(other: MilitaryUnit): Boolean {
    return type.isFast && !other.type.isFast && health == 1 && other.health > 1
  }

  // Source: https://forums.civfanatics.com/threads/fast-unit-retreat.84527/
  private fun MilitaryUnit.retreatProbabilityFrom(other: MilitaryUnit): Double {
    if (!canRetreatFrom(other)) return 0.0
    return rank.retreatFactor / (other.rank.retreatFactor + 50.0)
  }
}