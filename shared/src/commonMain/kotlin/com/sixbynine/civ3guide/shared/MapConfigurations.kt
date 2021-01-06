package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.Resource.*
import com.sixbynine.civ3guide.shared.Terrain.*

object MapConfigurations {

  val all: List<MapConfiguration> by lazy {
    listOf(
      MapConfiguration(
        width = 377,
        height = 191,
        tileWidth = 381 / 3f,
        tileHeight = 256 / 4f,
        tiles = listOf(
          TileInfo(192, 0, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(128, 30, PLAINS, hasRiver = true),
          TileInfo(67, 64, FOREST, coveredTerrain = PLAINS),
          TileInfo(128, 95, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(193, 127, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(257, 95, PLAINS, SUGAR, hasRiver = true),
          TileInfo(319, 64, HILLS, hasRiver = true),
          TileInfo(257, 32, GRASSLAND, hasRiver = true),
        ),
        image = MR.images.map0
      ),
      MapConfiguration(
        width = 382,
        height = 204,
        tileWidth = 374 / 3f,
        tileHeight = 186 / 3f,
        tiles = listOf(
          TileInfo(193, 6, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(137, 34, GRASSLAND, HORSES, hasRiver = true),
          TileInfo(64, 70, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(139, 107, GRASSLAND, hasRiver = true),
          TileInfo(192, 130, FOREST, coveredTerrain = GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(237, 105, GRASSLAND, hasRiver = true),
          TileInfo(252, 37, GRASSLAND, hasRiver = true),
          TileInfo(308, 65, FOREST, hasRiver = true, coveredTerrain = GRASSLAND),
        ),
        image = MR.images.map1
      ),
      MapConfiguration(
        width = 380,
        height = 189,
        tileWidth = 375 / 3f,
        tileHeight = 186 / 3f,
        tiles = listOf(
          TileInfo(193, 4, PLAINS, resource = CATTLE),
          TileInfo(127, 37, DESERT),
          TileInfo(68, 65, DESERT),
          TileInfo(131, 93, PLAINS, resource = WINE),
          TileInfo(190, 124, PLAINS),
          TileInfo(244, 98, PLAINS),
          TileInfo(244, 37, PLAINS),
          TileInfo(310, 65, PLAINS),
        ),
        image = MR.images.map2
      ),
      MapConfiguration(
        width = 382,
        height = 192,
        tileWidth = 378 / 3f,
        tileHeight = 188 / 3f,
        tiles = listOf(
          TileInfo(192, 4, BONUS_GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(265, 40, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(323, 70, PLAINS, resource = CATTLE, isIrrigatableViaCityOrLake = true),
          TileInfo(261, 93, GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(
            193,
            128,
            FOREST,
            coveredTerrain = GRASSLAND,
            resource = IVORY,
            isIrrigatableViaCityOrLake = true
          ),
          TileInfo(122, 99, FOREST, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(74, 71, GRASSLAND, hasRiver = true),
          TileInfo(139, 36, GRASSLAND, hasRiver = true),
        ),
        image = MR.images.map3
      ),
      MapConfiguration(
        width = 380,
        height = 190,
        tileWidth = 378 / 3f,
        tileHeight = 188 / 3f,
        tiles = listOf(
          TileInfo(191, 3, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(264, 39, FOREST, isIrrigatableViaCityOrLake = true, coveredTerrain = GRASSLAND),
          TileInfo(
            322,
            69,
            FOREST,
            resource = SILK,
            isIrrigatableViaCityOrLake = true,
            coveredTerrain = GRASSLAND
          ),
          TileInfo(260, 92, FOREST, resource = SILK, hasRiver = true, coveredTerrain = GRASSLAND),
          TileInfo(192, 127, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(121, 98, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(73, 70, GRASSLAND, hasRiver = true),
          TileInfo(139, 35, GRASSLAND, hasRiver = true),
        ),
        image = MR.images.map4
      ),
      MapConfiguration(
        width = 377,
        height = 188,
        tileWidth = 378 / 3f,
        tileHeight = 188 / 3f,
        tiles = listOf(
          TileInfo(188, 1, HILLS, hasRiver = true),
          TileInfo(261, 37, FOREST, resource = GAME, hasRiver = true, coveredTerrain = GRASSLAND),
          TileInfo(322, 69, FOREST, isIrrigatableViaCityOrLake = true, coveredTerrain = GRASSLAND),
          TileInfo(257, 90, FOREST, isIrrigatableViaCityOrLake = true, coveredTerrain = GRASSLAND),
          TileInfo(189, 125, DESERT, isIrrigatableViaCityOrLake = true),
          TileInfo(118, 96, DESERT, isIrrigatableViaCityOrLake = true),
          TileInfo(70, 68, DESERT, isIrrigatableViaCityOrLake = true),
          TileInfo(136, 33, HILLS, hasRiver = true),
        ),
        image = MR.images.map5
      ),
    )
  }
}