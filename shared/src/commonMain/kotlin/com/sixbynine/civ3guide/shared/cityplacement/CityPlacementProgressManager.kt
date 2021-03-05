package com.sixbynine.civ3guide.shared.cityplacement

import com.sixbynine.civ3guide.shared.level.LevelId.CITY_PLACEMENT
import com.sixbynine.civ3guide.shared.level.LevelManager
import com.sixbynine.civ3guide.shared.level.LevelPageData

object CityPlacementProgressManager {
  fun notePuzzleSolved(index: Int) {
    LevelManager.notePuzzleSolved(CITY_PLACEMENT, index)
  }

  fun getLevelPageData(): LevelPageData {
    return LevelManager.getLevelPageData(CITY_PLACEMENT, CityPlacementPuzzle.all.size)
  }
}