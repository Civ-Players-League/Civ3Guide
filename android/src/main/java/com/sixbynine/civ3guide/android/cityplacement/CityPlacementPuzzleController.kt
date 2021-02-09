package com.sixbynine.civ3guide.android.cityplacement

import com.sixbynine.civ3guide.shared.TileInfo
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementPuzzle

class CityPlacementPuzzleController {

  private val states = mutableListOf<CityPlacementPuzzleUiState>()
  private val observers = mutableSetOf<OnPuzzleStatesChangedObserver>()

  init {
    states.add(CityPlacementPuzzleUiState(CityPlacementPuzzle.all.first()))
  }

  fun getNumPuzzlesToDisplay(): Int {
    return states.size
  }

  fun getPuzzle(index: Int): CityPlacementPuzzleUiState {
    return states[index]
  }

  fun isLastPuzzle(index: Int): Boolean {
    return index >= CityPlacementPuzzle.all.size - 1
  }

  fun setSelectedTile(puzzleIndex: Int, tile: TileInfo?) {
    val previousPuzzle = states[puzzleIndex]
    if (previousPuzzle.selectedTile == tile) return
    val puzzle = previousPuzzle.copy(selectedTile = tile)

    states[puzzleIndex] = puzzle
    maybeAddNewPuzzle()
    notifyObservers()
  }

  private fun maybeAddNewPuzzle() {
    if (states.lastOrNull()?.isSolved() == true && states.size <= CityPlacementPuzzle.all.size - 1) {
      states.add(CityPlacementPuzzleUiState(CityPlacementPuzzle.all[states.size]))
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