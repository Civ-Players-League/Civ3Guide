package com.sixbynine.civ3guide.android.workerpuzzle

import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.worker.WorkerAction
import com.sixbynine.civ3guide.shared.worker.WorkerPuzzles

class PuzzleController {

  private val states = mutableListOf<PuzzleUiState>()
  private val observers = mutableSetOf<OnPuzzleStatesChangedObserver>()

  init {
    states.add(PuzzleUiState(WorkerPuzzles.all.first()))
  }

  fun getNumPuzzlesToDisplay(): Int {
    return states.size
  }

  fun getPuzzle(index: Int): PuzzleUiState {
    return states[index]
  }

  fun isLastPuzzle(index: Int): Boolean {
    return index >= WorkerPuzzles.all.size - 1
  }

  fun setWorkerAction(puzzleIndex: Int, action: WorkerAction?) {
    val previousPuzzle = states[puzzleIndex]
    if (previousPuzzle.selectedAction == action) return
    val puzzle = previousPuzzle.copy(selectedAction = action)

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
    if (states.lastOrNull()?.isSolved() == true && states.size <= WorkerPuzzles.all.size - 1) {
      states.add(PuzzleUiState(WorkerPuzzles.all[states.size]))
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