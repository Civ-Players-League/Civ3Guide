package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.Resource.*
import com.sixbynine.civ3guide.shared.Terrain.*
import com.sixbynine.civ3guide.shared.WorkerAction.*

object WorkerPuzzles {

  private fun workerPuzzleConfiguration(
    mapIndex: Int,
    optimalActionsSupplier: (TileInfo) -> List<WorkerAction>
  ): WorkerPuzzleConfiguration {
    val mapConfiguration = MapConfigurations.all[mapIndex]
    return WorkerPuzzleConfiguration(
      mapConfiguration,
      optimalActions = mapConfiguration.tiles.flatMap { tile ->
        optimalActionsSupplier(tile).map { tile to it }
      }.toSet()
    )
  }

  val all: List<WorkerPuzzleConfiguration> by lazy {
    listOf(
      workerPuzzleConfiguration(mapIndex = 0) {
        if (it.tile.resource == SUGAR) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 1) {
        if (it.tile.terrain == BONUS_GRASSLAND) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 2) {
        if (it.tile.resource == WINE) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 3) {
        if (it.tile.resource == CATTLE) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 4) {
        if (it.top == Point(191, 3)) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 5) {
        if (it.tile.resource == GAME) listOf(CLEAR_FOREST) else emptyList()
      },
    )
  }
}