package com.sixbynine.civ3guide.android.quiz

import com.sixbynine.civ3guide.shared.quiz.Quiz
import com.sixbynine.civ3guide.shared.quiz.QuizUiState
import com.sixbynine.civ3guide.shared.quiz.QuizUiState.Companion

class QuizController {

  private val quizStates = mutableListOf<QuizUiState>()
  private val observers = mutableSetOf<OnQuizStatesChangedObserver>()

  init {
    quizStates.add(QuizUiState.forQuizIndex(0))
  }

  fun getNumQuizzesToDisplay(): Int {
    return quizStates.size
  }

  fun getQuiz(index: Int): QuizUiState {
    return quizStates[index]
  }

  fun isLastQuiz(index: Int): Boolean {
    return index >= Quiz.all.size - 1
  }

  fun setSelectedIndex(quizIndex: Int, answerIndex: Int?) {
    val previousQuiz = quizStates[quizIndex]
    val quiz = previousQuiz.withSelectedAnswer(answerIndex)
    if (quiz == previousQuiz) return

    quizStates[quizIndex] = quiz
    if (quiz.selectedAnswer?.isCorrect == true && quizIndex == quizStates.size - 1) {
      if (quizIndex < Quiz.all.size - 1) {
        quizStates.add(QuizUiState.forQuizIndex(quizIndex + 1))
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