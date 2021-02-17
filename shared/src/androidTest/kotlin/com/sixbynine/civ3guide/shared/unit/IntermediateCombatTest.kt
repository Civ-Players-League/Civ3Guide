package com.sixbynine.civ3guide.shared.unit

import com.google.common.truth.Truth.assertThat
import com.sixbynine.civ3guide.shared.Terrain.*
import com.sixbynine.civ3guide.shared.Tile
import com.sixbynine.civ3guide.shared.combat.CombatCalculator.calculateIntermediateCombatResults
import com.sixbynine.civ3guide.shared.combat.CombatResultType.*
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.combat.IntermediateCombatResult
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.*
import org.junit.Test

class IntermediateCombatTest {

  @Test
  fun fairFight() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(SWORDSMAN),
        defender = veteran(SPEARMAN),
        terrain = HILLS,
      ),
      veteran(SWORDSMAN),
      veteran(SPEARMAN)
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 0.5, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 0.5, result = DEFENDER_WINS),
    )
  }

  @Test
  fun partialProbability() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(SWORDSMAN),
        defender = veteran(SPEARMAN),
        terrain = HILLS,
      ),
      attacker = veteran(SWORDSMAN),
      defender = veteran(SPEARMAN),
      probability = 0.5
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 0.25, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 0.25, result = DEFENDER_WINS),
    )
  }

  @Test
  fun baseDefenceBonus() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(WARRIOR),
        defender = veteran(WARRIOR),
        terrain = GRASSLAND,
      ),
      attacker = veteran(WARRIOR),
      defender = veteran(WARRIOR),
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 1 / 2.1, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 1.1 / 2.1, result = DEFENDER_WINS),
    )
  }

  @Test
  fun fortificationBonus() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(WARRIOR),
        defender = veteran(WARRIOR, isFortified = true),
        terrain = GRASSLAND,
      ),
      attacker = veteran(WARRIOR),
      defender = veteran(WARRIOR, isFortified = true),
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 1 / 2.35, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 1.35 / 2.35, result = DEFENDER_WINS),
    )
  }

  @Test
  fun acrossRiver() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(WARRIOR),
        defender = veteran(WARRIOR),
        terrain = MOUNTAIN,
        acrossRiver = true
      ),
      attacker = veteran(WARRIOR),
      defender = veteran(WARRIOR),
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 1 / 3.25, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 2.25 / 3.25, result = DEFENDER_WINS),
    )
  }

  @Test
  fun noRetreatWhenFullHealth() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(HORSEMAN),
        defender = veteran(SPEARMAN),
        terrain = PLAINS,
      ),
      attacker = veteran(HORSEMAN),
      defender = veteran(SPEARMAN),
    )

    assertThat(results.results).containsExactly(
      IntermediateCombatResult(probability = 2 / 4.2, result = ATTACKER_WINS),
      IntermediateCombatResult(probability = 2.2 / 4.2, result = DEFENDER_WINS),
    )
  }

  @Test
  fun noRetreatWhenAgainstFastUnit() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(HORSEMAN).copy(damage = 3),
        defender = veteran(HORSEMAN),
        terrain = PLAINS,
      ),
      attacker = veteran(HORSEMAN).copy(damage = 3),
      defender = veteran(HORSEMAN),
    )

    assertThat(results.p(ATTACKER_RETREATS)).isZero()
  }

  @Test
  fun noRetreatWhenDefendingCity() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(SWORDSMAN),
        defender = veteran(HORSEMAN).copy(damage = 3),
        terrain = PLAINS,
        cityDefenceBonus = 0.0
      ),
      attacker = veteran(SWORDSMAN),
      defender = veteran(HORSEMAN).copy(damage = 3),
      probability = 0.5
    )

    assertThat(results.p(DEFENDER_RETREATS)).isZero()
  }

  @Test
  fun canRetreatWhenNotDefendingCity() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(SWORDSMAN),
        defender = veteran(HORSEMAN),
        terrain = PLAINS,
      ),
      attacker = veteran(SWORDSMAN),
      defender = veteran(HORSEMAN).copy(damage = 3),
    )

    assertThat(results.p(DEFENDER_RETREATS)).isNonZero()
  }

  @Test
  fun retreat_veteran() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = veteran(HORSEMAN),
        defender = veteran(SPEARMAN),
        terrain = HILLS,
      ),
      attacker = veteran(HORSEMAN).copy(damage = 3),
      defender = veteran(SPEARMAN),
    )

    assertThat(results.p(ATTACKER_RETREATS)).isWithin(0.001).of(58 / 108.0)
    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(2 / 5.0 * 50 / 108.0)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(3 / 5.0 * 50 / 108.0)
  }

  @Test
  fun retreat_regularVsVeteran() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = regular(HORSEMAN),
        defender = veteran(SPEARMAN),
        terrain = HILLS,
      ),
      attacker = regular(HORSEMAN).copy(damage = 2),
      defender = veteran(SPEARMAN),
    )

    assertThat(results.p(ATTACKER_RETREATS)).isWithin(0.001).of(50 / 108.0)
    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(2 / 5.0 * 58 / 108.0)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(3 / 5.0 * 58 / 108.0)
  }

  @Test
  fun retreat_regularVsRegular() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = regular(HORSEMAN),
        defender = regular(SPEARMAN),
        terrain = HILLS,
      ),
      attacker = regular(HORSEMAN).copy(damage = 2),
      defender = regular(SPEARMAN),
    )

    assertThat(results.p(ATTACKER_RETREATS)).isWithin(0.001).of(0.5)
    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(2 / 5.0 * 0.5)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(3 / 5.0 * 0.5)
  }

  @Test
  fun reallyBadIdea() {
    val results = calculateIntermediateCombatResults(
      Engagement(
        attacker = conscript(WARRIOR),
        defender = elite(HOPLITE, isFortified = true),
        terrain = HILLS,
        cityDefenceBonus = 0.5,
        acrossRiver = true
      ),
      attacker = conscript(WARRIOR),
      defender = elite(HOPLITE, isFortified = true),
    )

    assertThat(results.p(ATTACKER_WINS)).isWithin(0.001).of(1 / 8.5)
    assertThat(results.p(DEFENDER_WINS)).isWithin(0.001).of(7.5 / 8.5)
  }
}