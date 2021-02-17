package com.sixbynine.civ3guide.shared.unit

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

/** Type of a (military) unit. */
interface UnitType {
  val label: StringResource

  val image: ImageResource

  val attack: Int

  val defence: Int

  val movement: Int

  val cost: Int

  val altLabel: StringResource?

  val altImage: ImageResource?

  val isFast: Boolean
    get() = movement >= 2
}