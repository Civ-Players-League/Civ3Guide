package com.sixbynine.civ3guide.shared.worker

import com.sixbynine.civ3guide.shared.level.LevelId.WORKER_ACTION
import com.sixbynine.civ3guide.shared.level.LevelManager
import com.sixbynine.civ3guide.shared.level.LevelPageData

object WorkerPuzzleProgressManager {

  fun notePuzzleSolved(index: Int) {
    LevelManager.notePuzzleSolved(WORKER_ACTION, index)
  }

  fun getLevelPageData(): LevelPageData {
    return LevelManager.getLevelPageData(WORKER_ACTION, WorkerPuzzles.all.size)
  }
}
