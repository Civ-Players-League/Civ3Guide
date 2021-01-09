package com.sixbynine.civ3guide.android.workerpuzzle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.core.view.get
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.*
import com.sixbynine.civ3guide.shared.StandardGovernment.DESPOTISM
import com.sixbynine.civ3guide.shared.mapview.MapTouchDelegate

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

  @SuppressLint("ClickableViewAccessibility")
  fun bind(state: PuzzleUiState, isLastPuzzle: Boolean) {
    val (configuration, selectedTile, selectedAction) = state
    val map = configuration.map

    image.setSharedImageResource(map.image)

    val pointMapper = getPointMapper(map)

    val touchDelegate = MapTouchDelegate(map)
      image.setOnTouchListener { _, event ->
      val touchPoint = pointMapper.fromGraphical(PointF(event.x, event.y))

      when (event.action) {
        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
          touchDelegate.onTouch(touchPoint)
        }
        MotionEvent.ACTION_UP -> {
          val tile = touchDelegate.onTouchEnd(touchPoint)
          onTileClickListener?.invoke(tile)
        }
        MotionEvent.ACTION_CANCEL -> {
          touchDelegate.onTouchEnd(touchPoint)
        }
      }
      true
    }

    if (selectedTile == null) {
      highlightView.setHighlightPoints(null, null, Color.BLACK)
    } else {
      val bounds = map.getBounds(selectedTile)
      highlightView.setHighlightPoints(
        pointMapper.toGraphical(bounds.left),
        pointMapper.toGraphical(bounds.top),
        Color.argb(83, 0, 0, 255)
      )
    }

    updateActionContainerButtons(selectedTile?.tile, selectedAction)

    val breakdown = if (selectedTile == null || selectedAction == null) {
      null
    } else {
      DESPOTISM.getOutputBreakdown(selectedTile.tile.withAction(selectedAction))
    }
    summaryView.bind(selectedTile?.tile?.terrain, breakdown)

    explanation.text = when {
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

  private fun getPointMapper(map: MapConfiguration) = object: PointMapper {
    val widthRatio: Float
      get() = image.width.toFloat() / map.width
    val heightRatio: Float
      get() = image.height.toFloat() / map.height
    val scaleRatio: Float
      get() = minOf(widthRatio, heightRatio)
    val extraX: Float
      get() = (image.width - map.width * scaleRatio) / 2
    val extraY: Float
      get() = (image.height - map.height * scaleRatio) / 2

    override fun toGraphical(point: Point): PointF {
      return PointF(
        extraX + point.x * scaleRatio,
        extraY + point.y * scaleRatio
      )
    }

    override fun fromGraphical(pointF: PointF): Point {
      return Point((pointF.x - extraX) / scaleRatio, (pointF.y - extraY) / scaleRatio)
    }
  }

  private interface PointMapper {
    fun toGraphical(point: Point): PointF

    fun fromGraphical(pointF: PointF): Point
  }

}