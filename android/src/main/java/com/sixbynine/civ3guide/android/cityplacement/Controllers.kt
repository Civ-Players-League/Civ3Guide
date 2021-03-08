package com.sixbynine.civ3guide.android.cityplacement

import com.sixbynine.civ3guide.shared.level.LevelPageRowData

fun LevelPageRowData.isLastIndex(index: Int): Boolean {
  val totalToDisplay = if (completed < total) {
    total - completed
  } else {
    total
  }
  return index >= totalToDisplay - 1
}