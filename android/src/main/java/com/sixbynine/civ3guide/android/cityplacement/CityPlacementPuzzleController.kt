package com.sixbynine.civ3guide.android.cityplacement

import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementProgressManager
import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementPuzzle
import com.sixbynine.civ3guide.shared.level.LevelManager.PUZZLES_PER_ROW
import com.sixbynine.civ3guide.shared.level.LevelPageRowData

class CityPlacementPuzzleController(val rowData: LevelPageRowData) {

  private val states = mutableListOf<CityPlacementPuzzleUiState>()
  private val observers = mutableSetOf<OnPuzzleStatesChangedObserver>()

  init {
    states.add(CityPlacementPuzzleUiState(CityPlacementPuzzle.all[rowData.launchIndex]))
  }

  fun getNumPuzzlesToDisplay(): Int {
    return states.size
  }

  fun getPuzzle(index: Int): CityPlacementPuzzleUiState {
    return states[index]
  }

  fun isLastPuzzle(index: Int) = rowData.isLastIndex(index)

  fun setSelectedTile(puzzleIndex: Int, tile: TileInfo?) {
    val previousPuzzle = states[puzzleIndex]
    if (previousPuzzle.selectedTile == tile) return
    val puzzle = previousPuzzle.copy(selectedTile = tile)

    if (puzzle.isSolved()) {
      CityPlacementProgressManager.notePuzzleSolved(states.size - 1 + rowData.launchIndex)
    }

    states[puzzleIndex] = puzzle
    maybeAddNewPuzzle()
    notifyObservers()
  }

  private fun maybeAddNewPuzzle() {
    if (states.lastOrNull()?.isSolved() == true && !isLastPuzzle(states.size - 1)) {
      states.add(
        CityPlacementPuzzleUiState(CityPlacementPuzzle.all[states.size + rowData.launchIndex])
      )
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