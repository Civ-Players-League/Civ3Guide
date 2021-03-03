package com.sixbynine.civ3guide.android.map

import android.graphics.PointF
import android.view.View
import com.sixbynine.civ3guide.shared.map.MapConfiguration
import com.sixbynine.civ3guide.shared.worker.Point

interface PointMapper {
  fun toGraphical(point: Point): PointF

  fun fromGraphical(pointF: PointF): Point
}

fun MapConfiguration.getPointMapper(image: View) = object: PointMapper {
  val widthRatio: Float
    get() = image.width.toFloat() / width
  val heightRatio: Float
    get() = image.height.toFloat() / height
  val scaleRatio: Float
    get() = minOf(widthRatio, heightRatio)
  val extraX: Float
    get() = (image.width - width * scaleRatio) / 2
  val extraY: Float
    get() = (image.height - height * scaleRatio) / 2

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