package com.sixbynine.civ3guide.android.workerpuzzle

import android.content.Context
import android.graphics.Outline
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.*
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.core.view.get
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.ktx.getColor
import com.sixbynine.civ3guide.android.ktx.getDrawable
import com.sixbynine.civ3guide.android.map.getPointMapper
import com.sixbynine.civ3guide.android.map.roundCorners
import com.sixbynine.civ3guide.android.map.setMapTouchDelegate
import com.sixbynine.civ3guide.shared.*
import com.sixbynine.civ3guide.shared.StandardGovernment.DESPOTISM
import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.tile.Tile
import com.sixbynine.civ3guide.shared.tile.TileOutputBreakdown
import com.sixbynine.civ3guide.shared.worker.WorkerAction

class PuzzleView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

  var onTileClickListener: ((TileInfo?) -> Unit)? = null
  var onActionButtonClickListener: ((WorkerAction) -> Unit)? = null
  var onNextDoneClickListener: (() -> Unit)? = null

  init {
    View.inflate(context, R.layout.view_puzzle_contents, this)
  }

  private val image = findViewById<ImageView>(R.id.image)
  private val actionContainer = findViewById<LinearLayout>(R.id.action_buttons_container)
  private val highlightView = findViewById<ClickHighlightView>(R.id.click_highlight_view)
  private val summaryView = findViewById<WorkerPuzzleSummaryView>(R.id.summary)
  private val explanation = findViewById<TextView>(R.id.explanation)
  private val button = findViewById<Button>(R.id.bottom_button)

  fun bind(state: PuzzleUiState, isLastPuzzle: Boolean) {
    val (configuration, selectedTile, selectedAction) = state
    val map = configuration.map

    image.setSharedImageResource(map.image)

    val pointMapper = map.getPointMapper(image)
    pointMapper.roundCorners(image)
    image.setMapTouchDelegate(map) { onTileClickListener?.invoke(it) }

    highlightView.clearHighlights()
    selectedTile?.let {
      val bounds = map.getBounds(it)
      highlightView.addHighlight(
        pointMapper.toGraphical(bounds.left),
        pointMapper.toGraphical(bounds.top),
        ClickHighlightView.COLOR_SELECTED
      )
    }

    updateActionContainerButtons(selectedTile?.tile, selectedAction)

    val outputTile: Tile?
    val breakdown: TileOutputBreakdown?
    if (selectedTile == null) {
      outputTile = null
      breakdown = null
    } else {
      outputTile = selectedTile.tile.withAction(selectedAction)
      breakdown = DESPOTISM.getOutputBreakdown(
        selectedTile.tile.withAction(selectedAction),
        isAgricultural = configuration.isAgricultural
      )
    }
    summaryView.bind(outputTile?.terrain, breakdown)

    explanation.text = when {
      state.isSolved() && configuration.extraExplanation != null -> {
        resources.getString(R.string.worker_action_optimal) + ".\n" + configuration.extraExplanation
      }
      state.isSolved() -> resources.getString(R.string.worker_action_optimal)
      selectedAction != null -> resources.getString(R.string.worker_action_not_optimal)
      else -> null
    }

    button.setText(if (isLastPuzzle) R.string.done else R.string.next)
    button.visibility = if (state.isSolved()) View.VISIBLE else View.GONE
    button.setOnClickListener { onNextDoneClickListener?.invoke() }
  }

  private fun updateActionContainerButtons(tile: Tile?, selectedAction: WorkerAction?) {
    if (tile == null) {
      actionContainer.removeAllViews()
      return
    }

    val actions = tile.getAvailableActions()

    val hasRightButtons =
      actionContainer.childCount == actions.size &&
          actions.indices.all { index ->
            (actionContainer[index] as WorkerActionButton).workerAction == actions[index]
          }
    if (!hasRightButtons) {
      actionContainer.removeAllViews()
      val inflater = LayoutInflater.from(actionContainer.context)
      actions.forEach { action ->
        val button =
          inflater.inflate(R.layout.view_worker_action_button, actionContainer, false)
              as WorkerActionButton
        button.workerAction = action
        actionContainer.addView(button)
      }
    }

    actionContainer.children.forEach { view ->
      check(view is WorkerActionButton)
      view.isChecked = view.workerAction == selectedAction
      view.actionClickListener = {
        onActionButtonClickListener?.invoke(it)
      }
    }
  }
}