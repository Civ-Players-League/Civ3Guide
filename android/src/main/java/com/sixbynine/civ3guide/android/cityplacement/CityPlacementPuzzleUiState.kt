package com.sixbynine.civ3guide.android.cityplacement

import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementAnswer
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementPuzzle

data class CityPlacementPuzzleUiState(
  val configuration: CityPlacementPuzzle,
  val selectedTile: TileInfo? = null,
) {
  val answer: CityPlacementAnswer?
    get() = selectedTile?.let { configuration.getAnswer(it) }

  fun isSolved() = answer?.isCorrect ?: false
}