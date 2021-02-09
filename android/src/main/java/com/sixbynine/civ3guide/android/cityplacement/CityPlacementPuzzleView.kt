package com.sixbynine.civ3guide.android.cityplacement

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.doOnNextLayout
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.map.getPointMapper
import com.sixbynine.civ3guide.android.map.setMapTouchDelegate
import com.sixbynine.civ3guide.android.workerpuzzle.ClickHighlightView
import com.sixbynine.civ3guide.shared.TileInfo
import com.sixbynine.civ3guide.shared.setSharedImageResource

class CityPlacementPuzzleView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

  var onTileClickListener: ((TileInfo?) -> Unit)? = null
  var onNextDoneClickListener: (() -> Unit)? = null

  init {
    View.inflate(context, R.layout.view_city_placement_puzzle_contents, this)
  }

  private val image = findViewById<ImageView>(R.id.image)
  private val highlightView = findViewById<ClickHighlightView>(R.id.click_highlight_view)
  private val explanation = findViewById<TextView>(R.id.explanation)
  private val button = findViewById<Button>(R.id.bottom_button)

  private var state: CityPlacementPuzzleUiState? = null
  private var isLastPuzzle: Boolean = false

  fun bind(state: CityPlacementPuzzleUiState, isLastPuzzle: Boolean) {
    this.state = state
    this.isLastPuzzle = isLastPuzzle
    bindViews()
  }

  private fun bindViews() {
    val state = state ?: return
    val (configuration, selectedTile) = state

    if (image.height == 0) {
      // The image height is needed to measure the size of the tiles for the highlight view.
      doOnNextLayout { bindViews() }
    }

    val map = configuration.map

    image.setSharedImageResource(map.image)

    val pointMapper = map.getPointMapper(image)
    image.setMapTouchDelegate(map) { onTileClickListener?.invoke(it) }

    highlightView.clearHighlights()
    map.tiles.forEach {
      val color = if (it == selectedTile) {
        ClickHighlightView.COLOR_SELECTED
      } else {
        ClickHighlightView.COLOR_SELECTABLE
      }

      val bounds = map.getBounds(it)
      highlightView.addHighlight(
        pointMapper.toGraphical(bounds.left),
        pointMapper.toGraphical(bounds.top),
        color
      )
    }

    explanation.text = state.answer?.explanation
    explanation.setTextColor(
      if (state.isSolved()) {
        getColor(R.color.colorTextCorrect)
      } else {
        getColor(R.color.colorTextIncorrect)
      }
    )

    button.setText(if (isLastPuzzle) R.string.done else R.string.next)
    button.visibility = if (state.isSolved()) View.VISIBLE else View.GONE
    button.setOnClickListener { onNextDoneClickListener?.invoke() }
  }

  @ColorInt
  private fun getColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(context, resId)
  }
}