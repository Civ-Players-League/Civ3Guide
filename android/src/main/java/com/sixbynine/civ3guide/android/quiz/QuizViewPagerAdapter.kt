package com.sixbynine.civ3guide.android.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.quiz.QuizController.OnQuizStatesChangedObserver
import com.sixbynine.civ3guide.android.quiz.QuizView.OnAnswerSelectedListener
import com.sixbynine.civ3guide.android.quiz.QuizView.OnQuizNextDoneClickedListener
import com.sixbynine.civ3guide.android.quiz.QuizViewPagerAdapter.ViewHolder

class QuizViewPagerAdapter(
  private val controller: QuizController,
  private val onQuizNextDoneClicked: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

  init {
    setHasStableIds(true)
  }

  private val recyclerViews = mutableSetOf<RecyclerView>()

  private val observer = object: OnQuizStatesChangedObserver {
    override fun onQuizStatesChanged() {
      recyclerViews.firstOrNull()?.post {
        notifyDataSetChanged()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.view_quiz, parent, false) as QuizView
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.view.bind(controller.getQuiz(position), controller.isLastQuiz(position))
    holder.view.onAnswerSelectedListener = object: OnAnswerSelectedListener {
      override fun onAnswerSelected(index: Int?) {
        controller.setSelectedIndex(position, index)
      }
    }
    holder.view.onNextDoneClickedListener = object: OnQuizNextDoneClickedListener {
      override fun onNextDoneClicked() {
        onQuizNextDoneClicked()
      }
    }
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getItemCount(): Int {
    return controller.getNumQuizzesToDisplay()
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    recyclerViews.add(recyclerView)
    controller.addObserver(observer)
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    recyclerViews.remove(recyclerView)
    if (recyclerViews.isEmpty()) {
      controller.removeObserver(observer)
    }
  }

  class ViewHolder(val view: QuizView) : RecyclerView.ViewHolder(view)
}