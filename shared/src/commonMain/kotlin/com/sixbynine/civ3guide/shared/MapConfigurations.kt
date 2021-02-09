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
      MapConfiguration(
        width = 514,
        height = 309,
        tileWidth = 514 / 4f,
        tileHeight = 256 / 4f,
        tiles = listOf(
          TileInfo(256, 82, HILLS, hasRiver = true),
          TileInfo(320, 116, FOREST, hasRiver = true, coveredTerrain = GRASSLAND),
          TileInfo(191, 116, GRASSLAND, resource = TOBACCO, hasRiver = true),
          TileInfo(256, 148, GRASSLAND, hasRiver = true),
          TileInfo(130, 148, BONUS_GRASSLAND),
          TileInfo(192, 180, HILLS),
        ),
        image = MR.images.map6
      ),
      MapConfiguration(
        width = 616,
        height = 307,
        tileWidth = 511 / 4f,
        tileHeight = 256 / 4f,
        tiles = listOf(
          TileInfo(325, 117, HILLS, resource = IRON),
          TileInfo(388, 85, GRASSLAND, resource = WHEAT),
          TileInfo(451, 117, GRASSLAND),
          TileInfo(324, 53, HILLS),
          TileInfo(260, 85, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(197, 117, FOREST, coveredTerrain = GRASSLAND),
        ),
        image = MR.images.map7
      ),
      MapConfiguration(
        width = 771,
        height = 451,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(435, 152, GRASSLAND, hasRiver = true),
          TileInfo(501, 187, FOREST, hasRiver = true, coveredTerrain = GRASSLAND),
          TileInfo(501, 121, FOREST, resource = GAME, hasRiver = true, coveredTerrain = GRASSLAND),
          TileInfo(565, 152, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(565, 218, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(629, 187, BONUS_GRASSLAND, hasRiver = true),
          ),
        image = MR.images.map8
      ),
      MapConfiguration(
        width = 718,
        height = 348,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(474, 91, HILLS),
          TileInfo(282, 125, PLAINS),
          TileInfo(408, 188, HILLS, resource = SUGAR, hasRiver = true),
          TileInfo(473, 154, HILLS, hasRiver = true),
          TileInfo(536, 188, HILLS, hasRiver = true),
          TileInfo(540, 127, GRASSLAND, hasRiver = true),
        ),
        image = MR.images.map9
      ),
      MapConfiguration(
        width = 643,
        height = 298,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(293, 119, FLOOD_PLAIN, hasRiver = true),
          TileInfo(357, 149, FLOOD_PLAIN, resource = WHEAT, hasRiver = true),
          TileInfo(421, 183, FLOOD_PLAIN, hasRiver = true),
          TileInfo(485, 152, FLOOD_PLAIN, hasRiver = true),
          TileInfo(421, 120, FLOOD_PLAIN, hasRiver = true),
          TileInfo(358, 87, FLOOD_PLAIN, hasRiver = true),
        ),
        image = MR.images.map10
      ),
      MapConfiguration(
        width = 646,
        height = 363,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(290, 236, HILLS, resource = GOLD),
          TileInfo(482, 204, PLAINS, resource = SUGAR),
          TileInfo(226, 204, BONUS_GRASSLAND),
          TileInfo(291, 172, HILLS),
          TileInfo(419, 172, PLAINS),
          TileInfo(355, 205, GRASSLAND),
        ),
        image = MR.images.map11
      ),
      MapConfiguration(
        width = 674,
        height = 347,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(393, 112, PLAINS, resource = IVORY, isIrrigatableViaCityOrLake = true),
          TileInfo(327, 79, GRASSLAND, resource = WHEAT),
          TileInfo(262, 111, GRASSLAND, resource = TOBACCO),
          TileInfo(327, 142, BONUS_GRASSLAND),
          TileInfo(518, 111, HILLS, hasRiver = true),
        ),
        image = MR.images.map12
      ),
      MapConfiguration(
        width = 766,
        height = 396,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(574, 83, GRASSLAND),
          TileInfo(513, 115, GRASSLAND),
          TileInfo(448, 81, BONUS_GRASSLAND),
          TileInfo(448, 147, GRASSLAND),
          TileInfo(576, 147, GRASSLAND),
          TileInfo(642, 115, GRASSLAND),
        ),
        image = MR.images.map13
      ),
      MapConfiguration(
        width = 724,
        height = 406,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(431, 144, GRASSLAND, resource = TOBACCO, hasRiver = true),
          TileInfo(301, 142, GRASSLAND, hasRiver = true),
          TileInfo(172, 144, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(363, 114, GRASSLAND, hasRiver = true),
          TileInfo(366, 175, GRASSLAND),
          TileInfo(238, 111, GRASSLAND),
        ),
        image = MR.images.map14
      ),
      MapConfiguration(
        width = 657,
        height = 334,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(214, 164, HILLS, hasRiver = true),
          TileInfo(150, 131, HILLS, hasRiver = true),
          TileInfo(276, 131, HILLS, resource = SUGAR, hasRiver = true),
          TileInfo(212, 100, FLOOD_PLAIN, hasRiver = true),
          TileInfo(278, 69, FLOOD_PLAIN, hasRiver = true),
          TileInfo(340, 100, HILLS, hasRiver = true),
        ),
        image = MR.images.map15
      ),
      MapConfiguration(
        width = 784,
        height = 369,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(307, 188, GRASSLAND, resource = TOBACCO, hasRiver = true),
          TileInfo(307, 253, FLOOD_PLAIN, resource = WHEAT, hasRiver = true),
          TileInfo(242, 158, FLOOD_PLAIN, hasRiver = true),
          TileInfo(371, 219, HILLS, hasRiver = true),
          TileInfo(371, 286, FLOOD_PLAIN, hasRiver = true),
          TileInfo(240, 224, FLOOD_PLAIN, hasRiver = true),
          TileInfo(179, 191, FLOOD_PLAIN, hasRiver = true),
          TileInfo(116, 160, FLOOD_PLAIN, hasRiver = true),
        ),
        image = MR.images.map16
      )
    )
  }
}