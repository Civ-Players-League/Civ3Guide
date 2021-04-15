package com.sixbynine.civ3guide.android.map

import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.doOnPreDraw
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.map.MapConfiguration
import com.sixbynine.civ3guide.shared.util.Dimensions
import com.sixbynine.civ3guide.shared.worker.Point

interface PointMapper {
  val scaleRatio: Float

  fun toGraphical(point: Point): PointF

  fun fromGraphical(pointF: PointF): Point
}

fun Dimensions.getPointMapper(image: View) = object: PointMapper {
  val widthRatio: Float
    get() = image.width.toFloat() / width
  val heightRatio: Float
    get() = image.height.toFloat() / height
  override val scaleRatio: Float
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

fun MapConfiguration.getPointMapper(image: View) = dimensions.getPointMapper(image)

fun PointMapper.roundCorners(image: ImageView) {
  val resources = image.resources
  image.doOnPreDraw {
    val drawable = image.drawable
    if (drawable is BitmapDrawable) {
      image.setImageDrawable(
        RoundedBitmapDrawableFactory.create(
          resources,
          drawable.bitmap
        ).also {
          it.cornerRadius =
            resources.getDimension(R.dimen.map_corner_radius) / scaleRatio
        }
      )
    }
  }
}