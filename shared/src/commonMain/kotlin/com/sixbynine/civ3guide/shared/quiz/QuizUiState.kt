package com.sixbynine.civ3guide.shared.quiz

import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.format
import com.sixbynine.civ3guide.shared.load

data class QuizUiState(
  val quiz: Quiz,
  val selectedIndex: Int? = null
) {

  val selectedAnswer: QuizAnswer?
    get() = selectedIndex?.let { quiz.answers[selectedIndex] }

  val isSolved: Boolean
    get() = selectedAnswer?.isCorrect == true

  fun withSelectedAnswer(index: Int?): QuizUiState {
    return copy(selectedIndex = index)
  }

  fun getExplanationText(): String? {
    val answer = selectedAnswer
    return when {
      answer == null -> null
      answer.isCorrect -> when (answer.explanation) {
        null -> strings.correct.load()
        else -> strings.correct_explanation.format(answer.explanation)
      }
      else -> when (answer.explanation) {
        null -> strings.incorrect.load()
        else -> strings.incorrect_explanation.format(answer.explanation)
      }
    }
  }

  companion object {
    fun forQuizIndex(index: Int): QuizUiState {
      return QuizUiState(quiz = Quiz.all[index])
    }
  }
}