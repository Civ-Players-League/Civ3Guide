package com.sixbynine.civ3guide.shared.mapview

import com.sixbynine.civ3guide.shared.MapConfiguration
import com.sixbynine.civ3guide.shared.Point
import com.sixbynine.civ3guide.shared.TileInfo

class MapTouchDelegate(private val map: MapConfiguration) {

  private var touchState: TouchState = TouchState.NotStarted

  fun onTouch(point: Point) {
    handleTouch(point)
  }

  fun onTouchEnd(point: Point): TileInfo? {
    handleTouch(point)
    val clickedTile = (touchState as? TouchState.Started)?.tile
    touchState = TouchState.NotStarted
    return clickedTile
  }

  private fun handleTouch(point: Point) {
    val tile = map.getTile(point)
    if (tile == null) {
      touchState = TouchState.Cancelled
      return
    }
    touchState = when (val localTouchState = touchState) {
        TouchState.NotStarted -> TouchState.Started(tile)
        TouchState.Cancelled -> TouchState.Cancelled
        is TouchState.Started -> when (localTouchState.tile) {
            tile -> TouchState.Started(tile)
            else -> TouchState.Cancelled
        }
    }
  }

  private sealed class TouchState {
    object NotStarted : TouchState()
    object Cancelled : TouchState()
    data class Started(val tile: TileInfo) : TouchState()
  }
}