package com.sixbynine.civ3guide.shared.worker

import com.sixbynine.civ3guide.shared.MR.images
import com.sixbynine.civ3guide.shared.map.MapConfiguration
import com.sixbynine.civ3guide.shared.map.TileInfo
import dev.icerock.moko.resources.ImageResource

class WorkerPuzzleConfiguration(
  val map: MapConfiguration,
  val isAgricultural: Boolean,
  val extraExplanation: String?,
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
  CLEAR_WETLANDS(images.clearWetlands),
  MINE(images.buildMine),
  IRRIGATE(images.irrigate),
  ROAD(images.buildRoad),
  RAILROAD(images.buildRailroad);
}

