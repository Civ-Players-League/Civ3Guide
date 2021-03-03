package com.sixbynine.civ3guide.android.workerpuzzle

import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.worker.WorkerAction
import com.sixbynine.civ3guide.shared.worker.WorkerPuzzleConfiguration

data class PuzzleUiState(
  val configuration: WorkerPuzzleConfiguration,
  val selectedTile: TileInfo? = null,
  val selectedAction: WorkerAction? = null
) {
  fun isSolved(): Boolean {
    return configuration.isOptimal(selectedTile, selectedAction)
  }
}