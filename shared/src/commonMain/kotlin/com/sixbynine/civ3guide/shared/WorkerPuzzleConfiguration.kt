package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.MR.images
import dev.icerock.moko.resources.ImageResource

class WorkerPuzzleConfiguration(
  val map: MapConfiguration,
  private val optimalActions: Set<Pair<TileInfo, WorkerAction>>
) {

  fun isOptimal(tile: TileInfo?, action: WorkerAction?): Boolean {
    if (tile == null || action == null) return false
    return (tile to action) in optimalActions
  }
}

data class Point(val x: Float, val y: Float) {
  constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
}

enum class WorkerAction(val image: ImageResource) {
  CLEAR_FOREST(images.clearForest),
  MINE(images.buildMine),
  IRRIGATE(images.irrigate),
  ROAD(images.buildRoad),
  RAILROAD(images.buildRailroad);
}
