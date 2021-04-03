package com.sixbynine.civ3guide.shared.combat

import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.combat.CombatResultType.ATTACKER_WINS
import com.sixbynine.civ3guide.shared.combat.CombatResultType.DEFENDER_WINS
import com.sixbynine.civ3guide.shared.load
import kotlin.math.roundToInt

object CombatExplainer {
  fun explain(engagement: Engagement): String {
    val (attacker, defender, terrain, acrossRiver, cityDefenceBonus) = engagement
    val explanation = StringBuilder()

    explanation
      .append("Attacking ")
      .append(attacker.type.label.load())
      .appendLine()
      .append("\tHP: ")
      .append(attacker.health)
      .appendLine()
      .append("\tCost: ")
      .append(attacker.type.cost)
      .appendLine()
      .append("\tAttack: ")
      .append(attacker.type.attack)
      .appendLine()

    explanation
      .appendLine()
      .append("Defending ")
      .append(defender.type.label.load())
      .appendLine()
      .append("\tHP: ")
      .append(defender.health)
      .appendLine()
      .append("\tCost: ")
      .append(defender.type.cost)
      .appendLine()
      .append("\tDefence: ")
      .append(defender.type.defence)
      .appendLine()


    explanation
      .appendLine()
      .append("Defence bonus:")
      .appendLine()
    var defenceMultiplier = 1.0
    if (defender.isFortified) {
      explanation.appendLine("\t+25% from fortification")
      defenceMultiplier += 0.25
    }
    if (acrossRiver) {
      explanation.appendLine("\t+25% from river")
      defenceMultiplier += 0.25
    }
    defenceMultiplier += terrain.getOutput().defenceBonus
    explanation
      .append("\t+")
      .append(terrain.getOutput().defenceBonus.printAsPercentage())
      .append(" from ")
      .appendLine(terrain.label.load())

    defenceMultiplier += cityDefenceBonus ?: 0.0
    if (cityDefenceBonus != null) {
      explanation
        .append("\t+")
        .append(cityDefenceBonus.printAsPercentage())
        .append(" from ")
      when (cityDefenceBonus) {
        0.0 -> explanation.appendLine(strings.town.load())
        0.5 -> explanation.appendLine(strings.town_walls.load())
        1.0 -> explanation.appendLine(strings.metropolis.load())
      }
    }

    explanation
      .appendLine()
      .append("Effective defence: ")
      .appendLine()
      .append("\t= ")
      .append(defender.type.defence)
      .append(" x ")
      .append(defenceMultiplier.printAsPercentage())
      .appendLine()
      .append("\t= ")
      .append((defender.type.defence * defenceMultiplier).roundTo2Decimals())
      .appendLine()

    val results = CombatCalculator.calculateCombatResults(engagement)

    explanation
      .appendLine()
      .append("Probability attacker wins: ")
      .append(results.p(ATTACKER_WINS).printAsPercentage())
      .appendLine()

    val eSString = results.expectedShields.let {
      if (it > 0) {
        "+${it.roundTo1Decimal()}"
      } else {
        it.roundTo1Decimal()
      }
    }

    explanation
      .appendLine()
      .appendLine("Expected Shields: ")
      .appendLine("Pw x Cd - Pl x Ca")
      .append("\t= ")
      .append(results.p(ATTACKER_WINS).printAsPercentage())
      .append(" x ")
      .append(defender.type.cost)
      .append(" - ")
      .append(results.p(DEFENDER_WINS).printAsPercentage())
      .append(" x ")
      .append(attacker.type.cost)
      .appendLine()
      .append("\t= ")
      .append((results.p(ATTACKER_WINS) * defender.type.cost).roundTo1Decimal())
      .append(" - ")
      .append((results.p(DEFENDER_WINS) * attacker.type.cost).roundTo1Decimal())
      .appendLine()
      .append("\t= ")
      .append(eSString)
      .appendLine()

    explanation
      .appendLine()
      .appendLine("Pw = probability of winning")
      .appendLine("Pl = probability of losing")
      .appendLine("Ca = attacker cost")
      .appendLine("Cd = defender cost")

    return explanation.toString()
  }
}

private fun Double.printAsPercentage(): String {
  return (100 * this).roundToInt().toString() + "%"
}

private fun Double.roundTo2Decimals(): String {
  if (this == this.roundToInt().toDouble()) {
    return roundToInt().toString()
  }
  return ((100 * this).roundToInt() / 100.0).toString()
}

private fun Double.roundTo1Decimal(): String {
  if (this == this.roundToInt().toDouble()) {
    return roundToInt().toString()
  }
  return ((10 * this).roundToInt() / 10.0).toString()
}


