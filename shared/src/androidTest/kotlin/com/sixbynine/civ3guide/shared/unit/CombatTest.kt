package com.sixbynine.civ3guide.shared.unit

import com.google.common.truth.Truth.assertThat
import com.sixbynine.civ3guide.shared.tile.Terrain.HILLS
import com.sixbynine.civ3guide.shared.tile.Terrain.PLAINS
import com.sixbynine.civ3guide.shared.combat.CombatCalculator
import com.sixbynine.civ3guide.shared.combat.CombatResult
import com.sixbynine.civ3guide.shared.combat.CombatResultType.*
import com.sixbynine.civ3guide.shared.combat.CombatResults
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.*
import org.junit.Test

class CombatTest {

  @Test
  fun fairFight() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(SWORDSMAN),
        veteran(SPEARMAN),
        HILLS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.5)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.5)
    // Spearman costs 20, Swordsman costs 30, but this is actually not great for attacker.
    assertThat(results.attackerFavorability).isWithin(0.001).of(0.667)
  }

  @Test
  fun retreatIsVeryGood() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(GALLIC_SWORDSMAN),
        veteran(SPEARMAN),
        HILLS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.45)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.24)
    assertThat(results.p(ATTACKER_RETREATS)).isWithin(0.001).of(0.31)
    // This would be better if Gallics weren't so damn expensive.
    assertThat(results.attackerFavorability).isWithin(0.001).of(1.104)
  }

  @Test
  fun veteranIsGood() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(SWORDSMAN),
        regular(SPEARMAN),
        HILLS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.65625)
  }

  @Test
  fun eliteIsVeryGood() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        elite(SWORDSMAN),
        regular(SPEARMAN),
        HILLS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.7734375)
  }

  @Test
  fun conscriptIsVeryBad() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        elite(SWORDSMAN),
        conscript(SPEARMAN),
        HILLS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.890625)
  }

  @Test
  fun spread() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        elite(HORSEMAN),
        regular(WARRIOR),
        PLAINS
      )
    )

    // Horseman wins
    assertThat(results.find(attacker = 5, defender = 0).probability).isWithin(0.001).of(0.269)
    assertThat(results.find(attacker = 4, defender = 0).probability).isWithin(0.001).of(0.286)
    assertThat(results.find(attacker = 3, defender = 0).probability).isWithin(0.001).of(0.203)
    assertThat(results.find(attacker = 2, defender = 0).probability).isWithin(0.001).of(0.120)
    assertThat(results.find(attacker = 1, defender = 0).probability).isWithin(0.001).of(0.049)
    // Warrior wins
    assertThat(results.find(attacker = 0, defender = 1).probability).isWithin(0.001).of(0.027)
    assertThat(results.find(attacker = 0, defender = 2).probability).isWithin(0.001).of(0.005)
    assertThat(results.find(attacker = 0, defender = 3).probability).isWithin(0.001).of(0.001)
    // Retreat
    assertThat(results.find(attacker = 1, defender = 2).probability).isWithin(0.001).of(0.029)
    assertThat(results.find(attacker = 1, defender = 3).probability).isWithin(0.001).of(0.01)
    // Total
    assertThat(results.results.sumByDouble { it.probability }).isWithin(0.001).of(1.0)
  }

  @Test
  fun myFollyInFirstMpt() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(ARCHER),
        veteran(ENKIDU_WARRIOR),
        PLAINS
      )
    )

    // Archer wins
    assertThat(results.find(attacker = 4, defender = 0).probability).isWithin(0.001).of(0.051)
    assertThat(results.find(attacker = 3, defender = 0).probability).isWithin(0.001).of(0.108)
    assertThat(results.find(attacker = 2, defender = 0).probability).isWithin(0.001).of(0.141)
    assertThat(results.find(attacker = 1, defender = 0).probability).isWithin(0.001).of(0.148)
    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.448)
    // Enkidu wins
    assertThat(results.find(attacker = 0, defender = 1).probability).isWithin(0.001).of(0.163)
    assertThat(results.find(attacker = 0, defender = 2).probability).isWithin(0.001).of(0.171)
    assertThat(results.find(attacker = 0, defender = 3).probability).isWithin(0.001).of(0.143)
    assertThat(results.find(attacker = 0, defender = 4).probability).isWithin(0.001).of(0.075)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.552)
    // Total
    assertThat(results.results.sumByDouble { it.probability }).isWithin(0.001).of(1.0)

    // Yeah, this was a bad idea...
    assertThat(results.attackerFavorability).isWithin(0.001).of(0.405)
  }

  @Test
  fun reallyBadIdea() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        attacker = conscript(WARRIOR),
        defender = elite(HOPLITE, isFortified = true),
        terrain = HILLS,
        cityDefenceBonus = 0.5,
        acrossRiver = true
      )
    )

    // This is really really terrible.
    assertThat(results.attackerFavorability).isWithin(0.00001).of(0.00024)
  }

  @Test
  fun reallyGoodIdea() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        attacker = elite(MOUNTED_WARRIOR),
        defender = regular(WARRIOR).copy(damage = 2),
        terrain = PLAINS
      )
    )

    // 240x is a good investment.
    assertThat(results.attackerFavorability).isWithin(0.001).of(239.459)
  }

  @Test
  fun onAnOpenFieldNed() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(MOUNTED_WARRIOR),
        conscript(SPEARMAN),
        PLAINS
      )
    )

    // Only 7% chance of winning, Lannisters never stood a chance...
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.071)
    assertThat(results.attackerFavorability).isWithin(0.001).of(7.791)
  }

  @Test
  fun attackingArchersIsGood() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(ARCHER),
        veteran(ARCHER),
        PLAINS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.792)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.208)
    assertThat(results.attackerFavorability).isWithin(0.001).of(3.810)
  }

  @Test
  fun attackingHorsemenIsALittleLessGood() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(ARCHER),
        veteran(HORSEMAN),
        PLAINS
      )
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(0.360)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.180)
    assertThat(results.p(DEFENDER_RETREATS)).isWithin(0.001).of(0.459)
    assertThat(results.attackerFavorability).isWithin(0.001).of(2.682)
  }

  @Test
  fun noAttackersRetreatWith1Health() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(HORSEMAN).copy(damage = 3),
        veteran(ARCHER),
        PLAINS
      )
    )

    assertThat(results.p(ATTACKER_RETREATS)).isWithin(0.001).of(0.0)
  }

  @Test
  fun noDefendersRetreatWith1Health() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        veteran(ARCHER),
        veteran(HORSEMAN).copy(damage = 3),
        PLAINS
      )
    )

    assertThat(results.p(DEFENDER_RETREATS)).isWithin(0.001).of(0.0)
  }

  @Test
  fun horseVsSword() {
    val results = CombatCalculator.calculateCombatResults(
      Engagement(
        attacker = regular(HORSEMAN),
        defender = elite(SWORDSMAN),
        terrain = PLAINS,
      )
    )

    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(0.387)
    assertThat(results.attackerFavorability).isWithin(0.001).of(1.0)
  }

  private fun CombatResults.find(attacker: Int, defender: Int): CombatResult {
    return results.find { it.attacker.health == attacker && it.defender.health == defender }!!
  }
}