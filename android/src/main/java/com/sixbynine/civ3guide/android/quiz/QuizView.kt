package com.sixbynine.civ3guide.android.quiz

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.ktx.checkAt
import com.sixbynine.civ3guide.android.ktx.indexOfChildWithId
import com.sixbynine.civ3guide.android.map.getPointMapper
import com.sixbynine.civ3guide.android.map.roundCorners
import com.sixbynine.civ3guide.shared.quiz.QuizUiState
import com.sixbynine.civ3guide.shared.setSharedImageResource

class QuizView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

  init {
    View.inflate(context, R.layout.view_quiz_contents, this)
  }

  private val title = findViewById<TextView>(R.id.title)
  private val image = findViewById<ImageView>(R.id.image)
  private val answers = findViewById<RadioGroup>(R.id.answers)
  private val explanation = findViewById<TextView>(R.id.explanation)
  private val bottomButton = findViewById<Button>(R.id.bottom_button)

  var onAnswerSelectedListener: OnAnswerSelectedListener? = null
  var onNextDoneClickedListener: OnQuizNextDoneClickedListener? = null

  fun bind(state: QuizUiState, isLastQuiz: Boolean) {
    title.text = state.quiz.question
    image.setSharedImageResource(state.quiz.image)
    state.quiz.imageDimensions.getPointMapper(image).roundCorners(image)
    state.quiz.answers.forEachIndexed { index, answer ->
      val radioButton = answers.getChildAt(index) as RadioButton
      radioButton.text = answer.text
      val shouldBeChecked = state.selectedAnswer == answer
      if (radioButton.isChecked != shouldBeChecked) {
        radioButton.isChecked = shouldBeChecked
      }
    }

    answers.setOnCheckedChangeListener { group, checkedId ->
      val index = group.indexOfChildWithId(checkedId)
      onAnswerSelectedListener?.onAnswerSelected(index)
    }
    answers.checkAt(state.selectedIndex)

    explanation.text = state.getExplanationText()
    explanation.setTextColor(
      if (state.isSolved) {
        getColor(R.color.colorTextCorrect)
      } else {
        getColor(R.color.colorTextIncorrect)
      }
    )

    bottomButton.setText(if (isLastQuiz) R.string.done else R.string.next)
    bottomButton.visibility = if (state.isSolved) View.VISIBLE else View.GONE
    bottomButton.setOnClickListener {
      onNextDoneClickedListener?.onNextDoneClicked()
    }

    if (state.isSolved) post { fullScroll(View.FOCUS_DOWN) }
  }

  interface OnAnswerSelectedListener {
    fun onAnswerSelected(index: Int?)
  }

  interface OnQuizNextDoneClickedListener {
    fun onNextDoneClicked()
  }

  @ColorInt
  private fun getColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(context, resId)
  }
}