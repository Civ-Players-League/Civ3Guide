package com.sixbynine.civ3guide.shared.cityplacement

import com.sixbynine.civ3guide.shared.MapConfiguration
import com.sixbynine.civ3guide.shared.MapConfigurations
import com.sixbynine.civ3guide.shared.TileInfo

data class CityPlacementPuzzle(
  val map: MapConfiguration,
  private val tileAnswers: Map<TileInfo, CityPlacementAnswer>
) {

  private constructor(map: MapConfiguration, vararg answers: CityPlacementAnswer) : this(
    map,
    map.tiles.zip(answers).toMap()
  )

  fun getAnswer(tile: TileInfo): CityPlacementAnswer {
    return tileAnswers[tile] ?: FALLBACK_WRONG_ANSWER
  }

  companion object {

    private val FALLBACK_WRONG_ANSWER = Answer("")
    private val WRONG_PLANT_COASTAL =
      Answer("Not here! Make sure to plant coastal, if you have the option.")
    private val WRONG_PLANT_RIVER =
      Answer("Not here! Plant on the river if you can.")

    val all: List<CityPlacementPuzzle> by lazy {
      listOf(
        CityPlacementPuzzle(
          MapConfigurations.all[6],
          Answer("Not here! Don't worry, planting on bonus commerce (the tobacco) is OK!"),
          WRONG_PLANT_COASTAL,
          Answer(
            "Yup! Great spot. Coastal, gets a lot of good land tiles, and is one the river.",
            isCorrect = true
          ),
          WRONG_PLANT_COASTAL,
          WRONG_PLANT_RIVER,
          WRONG_PLANT_RIVER
        )
      )
    }
  }
}

private typealias Answer = CityPlacementAnswer

data class CityPlacementAnswer(val explanation: String, val isCorrect: Boolean = false)