package com.sixbynine.civ3guide.android.quiz

import com.sixbynine.civ3guide.android.cityplacement.isLastIndex
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementProgressManager
import com.sixbynine.civ3guide.shared.level.LevelPageRowData
import com.sixbynine.civ3guide.shared.quiz.Quiz
import com.sixbynine.civ3guide.shared.quiz.QuizProgressManager
import com.sixbynine.civ3guide.shared.quiz.QuizUiState
import com.sixbynine.civ3guide.shared.quiz.QuizUiState.Companion

class QuizController(val rowData: LevelPageRowData) {

  private val quizStates = mutableListOf<QuizUiState>()
  private val observers = mutableSetOf<OnQuizStatesChangedObserver>()

  init {
    quizStates.add(QuizUiState.forQuizIndex(rowData.launchIndex))
  }

  fun getNumQuizzesToDisplay(): Int {
    return quizStates.size
  }

  fun getQuiz(index: Int): QuizUiState {
    return quizStates[index]
  }

  fun isLastQuiz(index: Int) = rowData.isLastIndex(index)

  fun setSelectedIndex(quizIndex: Int, answerIndex: Int?) {
    val previousQuiz = quizStates[quizIndex]
    val quiz = previousQuiz.withSelectedAnswer(answerIndex)
    if (quiz == previousQuiz) return

    if (quiz.isSolved) {
      QuizProgressManager.notePuzzleSolved(quizStates.size - 1 + rowData.launchIndex)
    }

    quizStates[quizIndex] = quiz
    if (quiz.selectedAnswer?.isCorrect == true && quizIndex == quizStates.size - 1) {
      if (!isLastQuiz(quizIndex)) {
        quizStates.add(QuizUiState.forQuizIndex(quizStates.size + rowData.launchIndex))
      }
    }
    notifyObservers()
  }

  fun addObserver(observer: OnQuizStatesChangedObserver) {
    observers.add(observer)
  }

  fun removeObserver(observer: OnQuizStatesChangedObserver) {
    observers.remove(observer)
  }

  private fun notifyObservers() {
    observers.toList().forEach {
      it.onQuizStatesChanged()
    }
  }

  interface OnQuizStatesChangedObserver {
    fun onQuizStatesChanged()
  }
}