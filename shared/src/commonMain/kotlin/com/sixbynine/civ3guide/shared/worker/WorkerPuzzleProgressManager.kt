package com.sixbynine.civ3guide.shared.worker

import com.sixbynine.civ3guide.shared.level.LevelData
import com.sixbynine.civ3guide.shared.level.LevelId.WORKER_ACTION
import com.sixbynine.civ3guide.shared.level.LevelManager
import com.sixbynine.civ3guide.shared.level.LeveledPuzzleId
import kotlin.math.ceil

object WorkerPuzzleProgressManager {

  const val PUZZLES_PER_ROW = 5

  fun shouldSkipLevelPage(): Boolean {
    return LevelManager.getLevelData(WORKER_ACTION).lastSolvedPuzzle == null
  }

  fun notePuzzleSolved(index: Int) {
    val id = LeveledPuzzleId(
      level = index / PUZZLES_PER_ROW,
      puzzle = index % PUZZLES_PER_ROW,
      index = index
    )
    getLastSolvedPuzzleId()?.let { if (it >= id) return }
    LevelManager.updateLevelData(WORKER_ACTION, LevelData(id))
  }

  fun getLevelPageData(): WorkerPuzzleLevelPageData {
    val lastSolvedPuzzleId = getLastSolvedPuzzleId()
    val numRows = ceil(WorkerPuzzles.all.size / PUZZLES_PER_ROW.toDouble()).toInt()
    val rows = (0 until numRows).map { rowIndex ->
      val puzzlesInRow = if (rowIndex == numRows - 1) {
        WorkerPuzzles.all.size % PUZZLES_PER_ROW
      } else {
        PUZZLES_PER_ROW
      }

      val numCompleted = if (lastSolvedPuzzleId == null) {
        0
      } else {
        when (rowIndex) {
          in 0 until lastSolvedPuzzleId.level -> puzzlesInRow
          lastSolvedPuzzleId.level -> lastSolvedPuzzleId.puzzle + 1
          else -> 0
        }
      }

      val launchIndex = if (numCompleted == puzzlesInRow) {
        rowIndex * PUZZLES_PER_ROW
      } else {
        rowIndex * PUZZLES_PER_ROW + numCompleted
      }

      val isUnlocked =
        numCompleted > 0 ||
            rowIndex == 0 ||
            (lastSolvedPuzzleId?.level == rowIndex - 1 &&
                lastSolvedPuzzleId.puzzle == PUZZLES_PER_ROW - 1)

      WorkerPuzzleLevelPageRowData(
        completed = numCompleted,
        total = puzzlesInRow,
        launchIndex = launchIndex,
        isLocked = !isUnlocked
      )
    }

    return WorkerPuzzleLevelPageData(rows)
  }

  private fun getLastSolvedPuzzleId(): LeveledPuzzleId? {
    return LevelManager.getLevelData(WORKER_ACTION).lastSolvedPuzzle
  }

}

data class WorkerPuzzleLevelPageData(
  val rows: List<WorkerPuzzleLevelPageRowData>
)

data class WorkerPuzzleLevelPageRowData(
  val completed: Int = 0,
  val total: Int = 0,
  val launchIndex: Int = 0,
  val isLocked: Boolean = false
)