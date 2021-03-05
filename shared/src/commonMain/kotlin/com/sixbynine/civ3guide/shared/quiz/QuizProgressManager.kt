package com.sixbynine.civ3guide.shared.quiz

import com.sixbynine.civ3guide.shared.level.LevelId.EARLY_GAME_QUIZ
import com.sixbynine.civ3guide.shared.level.LevelManager
import com.sixbynine.civ3guide.shared.level.LevelPageData

object QuizProgressManager {
  fun notePuzzleSolved(index: Int) {
    LevelManager.notePuzzleSolved(EARLY_GAME_QUIZ, index)
  }

  fun getLevelPageData(): LevelPageData {
    return LevelManager.getLevelPageData(EARLY_GAME_QUIZ, Quiz.all.size)
  }
}