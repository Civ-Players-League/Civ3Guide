package com.sixbynine.civ3guide.shared.unit

import com.sixbynine.civ3guide.shared.MR.images
import com.sixbynine.civ3guide.shared.MR.strings
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

/** Standard unit types available in standard game / MPT. */
enum class StandardUnitType(
  override val label: StringResource,
  override val image: ImageResource,
  override val attack: Int,
  override val defence: Int,
  override val movement: Int,
  override val cost: Int,
  override val isUnique: Boolean = false,
  override val altImage: ImageResource? = null,
  override val altLabel: StringResource? = null,
  override val isWheeled: Boolean = false
) : UnitType {
  ARCHER(strings.archer, images.unitArcher, 2, 1, 1, 20),
  BOWMAN(strings.bowman, images.unitBowman, 2, 2, 1, 20, isUnique = true),
  CHARIOT(strings.chariot, images.unitChariot, 1, 1, 2, 20, isWheeled = true),
  CHASQUI_SCOUT(strings.chasqui_scout, images.unitChasquiScout, 1, 1, 2, 20, isUnique = true),
  ENKIDU_WARRIOR(strings.enkidu_warrior, images.unitEnkiduWarrior, 1, 2, 1, 10, isUnique = true),
  GALLIC_SWORDSMAN(
    strings.gallic_swordsman,
    images.unitGallicSwordsman,
    3,
    2,
    2,
    40,
    isUnique = true,
    altImage = images.unitGarlicSwordsman,
    altLabel = strings.garlic_swordsman
  ),
  HOPLITE(strings.hoplite, images.unitHoplite, 1, 3, 1, 20, isUnique = true),
  HORSEMAN(strings.horseman, images.unitHorseman, 2, 1, 2, 30),
  IMMORTALS(strings.immortals, images.unitImmortals, 4, 2, 1, 30, isUnique = true),
  IMPI(strings.impi, images.unitImpi, 1, 2, 2, 20, isUnique = true),
  JAGUAR_WARRIOR(strings.jaguar_warrior, images.unitJaguarWarrior, 1, 1, 2, 15, isUnique = true),
  JAVELIN_THROWER(strings.javelin_thrower, images.unitJavelinThrower, 2, 2, 1, 30, isUnique = true),
  LEGIONARY(strings.legionary, images.unitLegionary, 3, 3, 1, 30, isUnique = true),
  MOUNTED_WARRIOR(strings.mounted_warrior, images.unitMountedWarrior, 3, 1, 2, 30, isUnique = true),
  SPEARMAN(strings.spearman, images.unitSpearman, 1, 2, 1, 20),
  SWORDSMAN(strings.swordsman, images.unitSwordsman, 3, 2, 1, 30),
  THREE_MAN_CHARIOT(
    strings.three_man_chariot,
    images.unitThreeManChariot,
    2,
    2,
    2,
    30,
    isUnique = true,
    isWheeled = true
  ),
  WAR_CHARIOT(
    strings.war_chariot,
    images.unitWarChariot,
    2,
    1,
    2,
    20,
    isUnique = true,
    isWheeled = true
  ),
  WARRIOR(strings.warrior, images.unitWarrior, 1, 1, 1, 10);
}