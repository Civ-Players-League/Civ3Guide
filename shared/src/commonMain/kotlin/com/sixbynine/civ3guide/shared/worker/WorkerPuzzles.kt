package com.sixbynine.civ3guide.shared.worker

import com.sixbynine.civ3guide.shared.map.MapConfigurations
import com.sixbynine.civ3guide.shared.tile.Resource.*
import com.sixbynine.civ3guide.shared.tile.Terrain.*
import com.sixbynine.civ3guide.shared.map.TileInfo
import com.sixbynine.civ3guide.shared.worker.WorkerAction.*

object WorkerPuzzles {

  private fun workerPuzzleConfiguration(
    mapIndex: Int,
    isAgricultural: Boolean = false,
    extraExplanation: String? = null,
    optimalActionsSupplier: (TileInfo) -> List<WorkerAction>
  ): WorkerPuzzleConfiguration {
    val mapConfiguration = MapConfigurations.all[mapIndex]
    return WorkerPuzzleConfiguration(
      mapConfiguration,
      isAgricultural,
      extraExplanation,
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
      workerPuzzleConfiguration(
        mapIndex = 4,
        extraExplanation = "This bonus grassland is on the same side of the river, so it will " +
            "provide more mobility when roaded."
      ) {
        if (it.top == Point(191, 3)) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 5,
        extraExplanation = "You have lots of production here, but are short on food, so chopping " +
            "and irrigating the game is the best move."
      ) {
        if (it.tile.resource == GAME) listOf(CLEAR_FOREST) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 21,
        isAgricultural = true,
        extraExplanation = "Agricultural civs get +2 food when irrigating desert, so irrigating " +
            "will still increase food output despite the Despotism penalty."
      ) {
        if (it.tile.resource == OASIS) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 22,
        extraExplanation = "You have lots of production from the forests, so it's best to " +
            "increase food output here."
      ) {
        if (it.tile.resource == CATTLE) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 23) {
        if (it.tile.terrain == BONUS_GRASSLAND) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 24) {
        if (it.tile.resource == SUGAR) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 25) {
        if (it.tile.resource == CATTLE) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 26,
        extraExplanation = "It's faster to mine plains than hills. Also, this puts your worker " +
            "in position to build an important road."
      ) {
        if (it.tile.resource == WINE) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 27,
        extraExplanation = "We have lots of food here, but not much production, so mining is " +
            "better than irrigating."
      ) {
        if (it.tile.resource == CATTLE) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 28,
        extraExplanation = "We're not short on food or shields, so prefer to increase food when " +
            "you can."
      ) {
        if (it.tile.resource == WHEAT) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 29) {
        if (it.tile.resource == CATTLE) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 30) {
        if (it.tile.terrain == BONUS_GRASSLAND) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 31) {
        if (it.tile.resource == CATTLE) listOf(IRRIGATE) else emptyList()
      },
      workerPuzzleConfiguration(mapIndex = 32) {
        if (it.tile.terrain == BONUS_GRASSLAND) listOf(MINE) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 33,
        extraExplanation = "You can't irrigate the banana, so it's best to get bonus commerce " +
            "from working the gems"
      ) {
        if (it.tile.resource == GEMS) listOf(CLEAR_WETLANDS) else emptyList()
      },
      workerPuzzleConfiguration(
        mapIndex = 34,
        extraExplanation = "The extra food from the banana means you'll benefit from irrigating " +
            "this tile after chopping."
      ) {
        if (it.tile.resource == BANANA) listOf(CLEAR_WETLANDS) else emptyList()
      },
    )
  }
}