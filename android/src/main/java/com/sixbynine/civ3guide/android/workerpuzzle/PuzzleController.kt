package com.sixbynine.civ3guide.android.workerpuzzle

import com.sixbynine.civ3guide.android.cityplacement.CityPlacementPuzzleUiState
import com.sixbynine.civ3guide.android.cityplacement.isLastIndex
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementProgressManager
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementPuzzle
import com.sixbynine.civ3guide.shared.level.LevelPageRowData
import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.worker.WorkerAction
import com.sixbynine.civ3guide.shared.worker.WorkerPuzzleProgressManager
import com.sixbynine.civ3guide.shared.worker.WorkerPuzzles

class PuzzleController(val rowData: LevelPageRowData) {

  private val states = mutableListOf<PuzzleUiState>()
  private val observers = mutableSetOf<OnPuzzleStatesChangedObserver>()

  init {
    states.add(PuzzleUiState(WorkerPuzzles.all[rowData.launchIndex]))
  }

  fun getNumPuzzlesToDisplay(): Int {
    return states.size
  }

  fun getPuzzle(index: Int): PuzzleUiState {
    return states[index]
  }

  fun isLastPuzzle(index: Int) = rowData.isLastIndex(index)

  fun setWorkerAction(puzzleIndex: Int, action: WorkerAction?) {
    val previousPuzzle = states[puzzleIndex]
    if (previousPuzzle.selectedAction == action) return
    val puzzle = previousPuzzle.copy(selectedAction = action)

    if (puzzle.isSolved()) {
      WorkerPuzzleProgressManager.notePuzzleSolved(states.size - 1 + rowData.launchIndex)
    }

    states[puzzleIndex] = puzzle
    maybeAddNewPuzzle()
    notifyObservers()
  }

  fun setWorkerTile(puzzleIndex: Int, tile: TileInfo?) {
    val previousPuzzle = states[puzzleIndex]
    if (previousPuzzle.selectedTile == tile) return
    val puzzle = previousPuzzle.copy(
      selectedTile = tile,
      selectedAction = null
    )

    states[puzzleIndex] = puzzle
    maybeAddNewPuzzle()
    notifyObservers()
  }

  private fun maybeAddNewPuzzle() {
    if (states.lastOrNull()?.isSolved() == true && !isLastPuzzle(states.size - 1)) {
      states.add(PuzzleUiState(WorkerPuzzles.all[states.size + rowData.launchIndex]))
    }
  }

  fun addObserver(observer: OnPuzzleStatesChangedObserver) {
    observers.add(observer)
  }

  fun removeObserver(observer: OnPuzzleStatesChangedObserver) {
    observers.remove(observer)
  }

  private fun notifyObservers() {
    observers.toList().forEach {
      it.onPuzzleStatesChanged()
    }
  }

  interface OnPuzzleStatesChangedObserver {
    fun onPuzzleStatesChanged()
  }
}