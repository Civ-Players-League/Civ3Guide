package com.sixbynine.civ3guide.android.map

import android.annotation.SuppressLint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import com.sixbynine.civ3guide.shared.map.MapConfiguration
import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.map.MapTouchDelegate

@SuppressLint("ClickableViewAccessibility")
fun View.setMapTouchDelegate(
  map: MapConfiguration,
  onTileClickListener: (TileInfo?) -> Unit
) {
  val touchDelegate = MapTouchDelegate(map)
  val pointMapper = map.getPointMapper(this)
  setOnTouchListener { _, event ->
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
}