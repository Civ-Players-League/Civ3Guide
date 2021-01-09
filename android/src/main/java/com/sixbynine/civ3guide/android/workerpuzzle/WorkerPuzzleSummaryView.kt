package com.sixbynine.civ3guide.android.workerpuzzle

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.Terrain
import com.sixbynine.civ3guide.shared.TileOutputBreakdown
import com.sixbynine.civ3guide.shared.setTextResource

class WorkerPuzzleSummaryView(
  context: Context,
  attrs: AttributeSet?
) : LinearLayout(context, attrs) {

  init {
    orientation = VERTICAL
  }

  fun bind(terrain: Terrain?, breakdown: TileOutputBreakdown?) {
    removeAllViews()
    if (terrain == null || breakdown == null) {
      return
    }

    inflateRow {
      label.setTextResource(terrain.label)
      foodLabel.text = String.format("%d", breakdown.baseOutput.food)
      shieldsLabel.text = String.format("%d", breakdown.baseOutput.shields)
      commerceLabel.text = String.format("%d", breakdown.baseOutput.commerce)
    }

    breakdown.modifiers.forEach { modifier ->
      inflateRow {
        label.setTextResource(modifier.label)
        applyModifierEffect(foodGroup, foodLabel, modifier.effect.food)
        applyModifierEffect(shieldsGroup, shieldsLabel, modifier.effect.shields)
        applyModifierEffect(commerceGroup, commerceLabel, modifier.effect.commerce)
      }
    }

    if (breakdown.modifiers.isEmpty()) return

    val divider = View(context)
    divider.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDivider))
    addView(
      divider,
      LayoutParams(
        LayoutParams.MATCH_PARENT,
        resources.getDimensionPixelSize(R.dimen.divider_height)
      )
    )

    inflateRow {
      label.setText(R.string.total)
      foodLabel.text = String.format("%d", breakdown.totalOutput.food)
      shieldsLabel.text = String.format("%d", breakdown.totalOutput.shields)
      commerceLabel.text = String.format("%d", breakdown.totalOutput.commerce)
    }
  }

  private inline fun inflateRow(modifier: Row.() -> Unit) {
    LayoutInflater.from(context).inflate(R.layout.puzzle_summary_row, this, false).let {
      addView(it)
      Row(it).modifier()
    }
  }

  fun applyModifierEffect(group: View, label: TextView, value: Int) {
    when {
      value == 0 -> group.visibility = View.INVISIBLE
      value > 0 -> label.text = String.format("+%d", value)
      else -> label.text = String.format("%d", value)
    }
  }

  private class Row(view: View) {
    val label = view.findViewById<TextView>(R.id.label)
    val foodGroup = view.findViewById<View>(R.id.food_group)
    val foodLabel = view.findViewById<TextView>(R.id.food_label)
    val shieldsGroup = view.findViewById<View>(R.id.shields_group)
    val shieldsLabel = view.findViewById<TextView>(R.id.shields_label)
    val commerceGroup = view.findViewById<View>(R.id.commerce_group)
    val commerceLabel = view.findViewById<TextView>(R.id.commerce_label)
  }
}