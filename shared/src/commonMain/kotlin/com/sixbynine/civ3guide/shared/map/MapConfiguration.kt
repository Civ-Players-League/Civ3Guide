package com.sixbynine.civ3guide.shared.map

import com.sixbynine.civ3guide.shared.tile.Resource
import com.sixbynine.civ3guide.shared.tile.Terrain
import com.sixbynine.civ3guide.shared.tile.Tile
import com.sixbynine.civ3guide.shared.util.Dimensions
import com.sixbynine.civ3guide.shared.worker.Point
import dev.icerock.moko.resources.ImageResource
import kotlin.math.abs

data class MapConfiguration(
  val width: Int,
  val height: Int,
  val tileWidth: Float = 640 / 5f,
  val tileHeight: Float = 385 / 6f,
  val tiles: List<TileInfo>,
  val image: ImageResource
) {

  val dimensions: Dimensions
    get() = Dimensions(width = width, height = height)

  fun getBounds(tile: TileInfo): TileBounds {
    val top = tile.top
    val left = Point(x = top.x - tileWidth / 2, y = top.y + tileHeight / 2)
    return TileBounds(
      left = left,
      top = top,
      right = left.copy(x = left.x + tileWidth),
      bottom = top.copy(y = top.y + tileHeight)
    )
  }

  fun getTile(point: Point) = tiles.firstOrNull { point in getBounds(it) }

  fun getTile(x: Number, y: Number) = tiles.firstOrNull { Point(x, y) in getBounds(it) }
}

data class TileInfo(
  val top: Point,
  val tile: Tile,
) {

  constructor(
    x: Int,
    y: Int,
    terrain: Terrain,
    resource: Resource? = null,
    hasRiver: Boolean = false,
    coveredTerrain: Terrain? = null,
    isIrrigatableViaCityOrLake: Boolean = false,
  ) :
      this(
        Point(x, y),
        Tile(
          terrain,
          resource = resource,
          hasRiver = hasRiver,
          coveredTerrain = coveredTerrain,
          isIrrigatableViaCity = isIrrigatableViaCityOrLake
        ),
      )
}

data class TileBounds(
  val left: Point,
  val top: Point,
  val right: Point,
  val bottom: Point
) {
  val center get() = Point(x = top.x, y = left.y)

  private val tileWidth get() = right.x - left.x
  private val tileHeight get() = bottom.y - top.y

  operator fun contains(point: Point): Boolean {
    val yDelta = abs(point.y - center.y) / (tileHeight / 2)
    val xRangeLeft = left.x + yDelta * (tileHeight)
    if (xRangeLeft >= center.x) return false
    val xRangeRight = left.x + tileWidth - (xRangeLeft - left.x)
    return point.x in xRangeLeft..xRangeRight
  }
}