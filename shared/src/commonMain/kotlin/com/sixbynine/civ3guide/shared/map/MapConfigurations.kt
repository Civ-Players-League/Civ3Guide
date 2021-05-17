package com.sixbynine.civ3guide.shared.map

import com.sixbynine.civ3guide.shared.MR.images
import com.sixbynine.civ3guide.shared.tile.Resource.*
import com.sixbynine.civ3guide.shared.tile.Terrain.*

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
        image = images.map0
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
        image = images.map1
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
        image = images.map2
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
        image = images.map3
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
        image = images.map4
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
        image = images.map5
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
        image = images.map6
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
        image = images.map7
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
        image = images.map8
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
        image = images.map9
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
        image = images.map10
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
        image = images.map11
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
        image = images.map12
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
        image = images.map13
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
        image = images.map14
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
        image = images.map15
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
        image = images.map16
      ),
      MapConfiguration(
        width = 663,
        height = 397,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(345, 233, TUNDRA),
          TileInfo(345, 169, GRASSLAND),
          TileInfo(406, 204, GRASSLAND, resource = WINE),
          TileInfo(473, 233, TUNDRA),
          TileInfo(407, 266, TUNDRA),
          TileInfo(278, 200, TUNDRA),
        ),
        image = images.map17
      ),
      MapConfiguration(
        width = 677,
        height = 411,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(251, 120, PLAINS),
          TileInfo(188, 152, DESERT, resource = OASIS),
          TileInfo(252, 57, PLAINS),
          TileInfo(316, 89, PLAINS),
          TileInfo(381, 122, PLAINS, resource = IVORY),
          TileInfo(314, 152, HILLS),
          TileInfo(252, 185, FOREST, coveredTerrain = PLAINS),
        ),
        image = images.map18
      ),
      MapConfiguration(
        width = 616,
        height = 345,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(295, 158, FOREST, coveredTerrain = TUNDRA),
          TileInfo(295, 222, FOREST, resource = GAME, coveredTerrain = TUNDRA),
          TileInfo(358, 190, FOREST, coveredTerrain = TUNDRA),
          TileInfo(359, 125, FOREST, coveredTerrain = TUNDRA),
          TileInfo(165, 158, FOREST, coveredTerrain = TUNDRA),
          TileInfo(231, 127, TUNDRA),
        ),
        image = images.map19
      ),
      MapConfiguration(
        width = 710,
        height = 351,
        tileWidth = 640 / 5f,
        tileHeight = 385 / 6f,
        tiles = listOf(
          TileInfo(416, 180, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(415, 114, FOREST, coveredTerrain = PLAINS),
          TileInfo(350, 146, PLAINS),
          TileInfo(350, 80, PLAINS),
          TileInfo(285, 113, PLAINS),
          TileInfo(416, 49, FOREST, coveredTerrain = PLAINS),
          TileInfo(350, 17, PLAINS),
        ),
        image = images.map20
      ),
      MapConfiguration(
        width = 385,
        height = 193,
        tiles = listOf(
          TileInfo(190, 0, PLAINS, hasRiver = true),
          TileInfo(256, 30, PLAINS, hasRiver = true),
          TileInfo(320, 64, PLAINS, hasRiver = true),
          TileInfo(255, 96, FOREST, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(190, 127, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(126, 95, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(65, 63, FOREST, coveredTerrain = PLAINS),
          TileInfo(127, 30, DESERT, resource = OASIS, isIrrigatableViaCityOrLake = true),
        ),
        image = images.map21
      ),
      MapConfiguration(
        width = 379,
        height = 189,
        tiles = listOf(
          TileInfo(188, 2, BONUS_GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(251, 33, GRASSLAND, resource = CATTLE, hasRiver = true),
          TileInfo(315, 64, GRASSLAND, hasRiver = true),
          TileInfo(251, 96, FOREST, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(186, 127, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(122, 95, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(61, 63, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(123, 30, GRASSLAND, hasRiver = true),
        ),
        image = images.map22
      ),
      MapConfiguration(
        width = 376,
        height = 188,
        tiles = listOf(
          TileInfo(188, 0, PLAINS, hasRiver = true),
          TileInfo(251, 30, GRASSLAND, resource = SILK, hasRiver = true),
          TileInfo(251, 93, FOREST, resource = SILK, coveredTerrain = GRASSLAND),
          TileInfo(186, 124, FOREST, resource = SILK, coveredTerrain = GRASSLAND),
          TileInfo(122, 92, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(61, 60, GRASSLAND, hasRiver = true),
          TileInfo(123, 30, GRASSLAND, hasRiver = true),
        ),
        image = images.map23
      ),
      MapConfiguration(
        width = 378,
        height = 185,
        tiles = listOf(
          TileInfo(251, 33, PLAINS, resource = SUGAR),
          TileInfo(315, 64, PLAINS),
          TileInfo(251, 96, BONUS_GRASSLAND),
          TileInfo(186, 127, GRASSLAND),
          TileInfo(122, 95, GRASSLAND),
          TileInfo(123, 30, GRASSLAND),
        ),
        image = images.map24
      ),
      MapConfiguration(
        width = 382,
        height = 195,
        tiles = listOf(
          TileInfo(188, 2, GRASSLAND, resource = CATTLE),
          TileInfo(251, 33, BONUS_GRASSLAND),
          TileInfo(122, 95, FOREST, coveredTerrain = PLAINS),
          TileInfo(61, 63, PLAINS),
          TileInfo(123, 30, GRASSLAND),
        ),
        image = images.map25
      ),
      MapConfiguration(
        width = 375,
        height = 193,
        tiles = listOf(
          TileInfo(250, 35, HILLS, resource = SUGAR),
          TileInfo(185, 129, PLAINS),
          TileInfo(121, 97, PLAINS),
          TileInfo(60, 65, PLAINS, resource = WINE),
        ),
        image = images.map26
      ),
      MapConfiguration(
        width = 380,
        height = 186,
        tiles = listOf(
          TileInfo(188, 2, GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(251, 33, GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(315, 64, GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(186, 127, GRASSLAND, resource = CATTLE, isIrrigatableViaCityOrLake = true),
          TileInfo(122, 95, GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(123, 30, GRASSLAND, isIrrigatableViaCityOrLake = true),
        ),
        image = images.map27
      ),
      MapConfiguration(
        width = 370,
        height = 200,
        tiles = listOf(
          TileInfo(184, 12, HILLS),
          TileInfo(247, 43, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(311, 74, PLAINS, hasRiver = true),
          TileInfo(247, 106, PLAINS, resource = WHEAT, hasRiver = true),
          TileInfo(182, 137, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(118, 105, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(57, 73, HILLS),
          TileInfo(119, 40, HILLS, resource = GOLD),
        ),
        image = images.map28
      ),
      MapConfiguration(
        width = 380,
        height = 195,
        tiles = listOf(
          TileInfo(188, 8, BONUS_GRASSLAND, isIrrigatableViaCityOrLake = true),
          TileInfo(251, 39, MOUNTAIN, hasRiver = true),
          TileInfo(315, 70, HILLS, resource = GOLD, hasRiver = true),
          TileInfo(251, 102, MOUNTAIN, hasRiver = true),
          TileInfo(186, 133, PLAINS, hasRiver = true),
          TileInfo(122, 101, FOREST, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(61, 69, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(123, 36, GRASSLAND, resource = CATTLE, isIrrigatableViaCityOrLake = true),
        ),
        image = images.map29
      ),
      MapConfiguration(
        width = 379,
        height = 192,
        tiles = listOf(
          TileInfo(188, 4, DESERT),
          TileInfo(251, 35, HILLS),
          TileInfo(315, 66, BONUS_GRASSLAND),
          TileInfo(61, 65, PLAINS),
          TileInfo(123, 32, PLAINS, resource = CATTLE),
        ),
        image = images.map30
      ),
      MapConfiguration(
        width = 373,
        height = 190,
        tiles = listOf(
          TileInfo(185, 0, PLAINS, hasRiver = true),
          TileInfo(248, 31, PLAINS, resource = HORSES, hasRiver = true),
          TileInfo(312, 62, MOUNTAIN),
          TileInfo(248, 94, PLAINS, isIrrigatableViaCityOrLake = true),
          TileInfo(183, 125, PLAINS, resource = CATTLE, isIrrigatableViaCityOrLake = true),
          TileInfo(119, 93, PLAINS, hasRiver = true),
          TileInfo(58, 61, FOREST, coveredTerrain = PLAINS, hasRiver = true),
          TileInfo(120, 28, PLAINS, hasRiver = true),
        ),
        image = images.map31
      ),
      MapConfiguration(
        width = 381,
        height = 205,
        tiles = listOf(
          TileInfo(190, 10, MOUNTAIN, hasRiver = true),
          TileInfo(253, 39, JUNGLE, resource = BANANA, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(317, 72, BONUS_GRASSLAND, hasRiver = true),
          TileInfo(253, 104, MOUNTAIN, hasRiver = true),
          TileInfo(188, 135, GRASSLAND, hasRiver = true),
          TileInfo(124, 103, MOUNTAIN, hasRiver = true),
          TileInfo(63, 71, JUNGLE, resource = SPICE, coveredTerrain = GRASSLAND),
          TileInfo(125, 38, JUNGLE, coveredTerrain = GRASSLAND),
        ),
        image = images.map32
      ),
      MapConfiguration(
        width = 371,
        height = 195,
        tiles = listOf(
          TileInfo(256, 34, HILLS),
          TileInfo(320, 67, HILLS, resource = GOLD),
          TileInfo(256, 99, JUNGLE, coveredTerrain = GRASSLAND),
          TileInfo(191, 130, JUNGLE, resource = GEMS, coveredTerrain = GRASSLAND),
          TileInfo(127, 98, JUNGLE, coveredTerrain = GRASSLAND),
          TileInfo(66, 66, JUNGLE, resource = BANANA, coveredTerrain = GRASSLAND),
        ),
        image = images.map33
      ),
      MapConfiguration(
        width = 388,
        height = 199,
        tiles = listOf(
          TileInfo(196, 8, HILLS, hasRiver = true),
          TileInfo(259, 37, JUNGLE, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(323, 70, JUNGLE, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(259, 102, JUNGLE, coveredTerrain = GRASSLAND, resource = SPICE, hasRiver = true),
          TileInfo(131, 36, JUNGLE, resource = BANANA, coveredTerrain = GRASSLAND, hasRiver = true),
        ),
        image = images.map34
      ),
      MapConfiguration(
        width = 381,
        height = 195,
        tiles = listOf(
          TileInfo(189, 3, GRASSLAND),
          TileInfo(253, 32, GRASSLAND, resource = CATTLE),
          TileInfo(317, 64, GRASSLAND),
          TileInfo(252, 98, BONUS_GRASSLAND),
          TileInfo(189, 128, GRASSLAND),
          TileInfo(125, 97, GRASSLAND),
          TileInfo(128, 33, GRASSLAND),
        ),
        image = images.map35
      ),
      MapConfiguration(
        width = 383,
        height = 192,
        tiles = listOf(
          TileInfo(190, 4, DESERT, resource = OASIS, isIrrigatableViaCityOrLake = true),
          TileInfo(253, 33, PLAINS, hasRiver = true),
          TileInfo(317, 66, PLAINS, hasRiver = true),
          TileInfo(253, 98, PLAINS, hasRiver = true),
          TileInfo(188, 129, PLAINS, resource = SUGAR, hasRiver = true),
          TileInfo(124, 97, PLAINS, resource = SUGAR, hasRiver = true),
          TileInfo(125, 32, PLAINS, isIrrigatableViaCityOrLake = true),
        ),
        image = images.map36
      ),
      MapConfiguration(
        width = 384,
        height = 191,
        tiles = listOf(
          TileInfo(192, 1, BONUS_GRASSLAND),
          TileInfo(255, 30, GRASSLAND),
          TileInfo(319, 63, GRASSLAND, resource = WINE),
          TileInfo(255, 95, GRASSLAND),
          TileInfo(190, 126, PLAINS),
          TileInfo(126, 94, GRASSLAND, resource = WHEAT),
          TileInfo(65, 62, GRASSLAND),
          TileInfo(127, 29, PLAINS),
        ),
        image = images.map37
      ),
      MapConfiguration(
        width = 390,
        height = 195,
        tiles = listOf(
          TileInfo(192, 1, FOREST, resource = GAME, coveredTerrain = PLAINS),
          TileInfo(255, 30, FOREST, coveredTerrain = GRASSLAND),
          TileInfo(255, 95, FOREST, coveredTerrain = GRASSLAND, hasRiver = true),
          TileInfo(190, 126, FLOOD_PLAIN, resource = WHEAT, hasRiver = true),
          TileInfo(126, 94, FLOOD_PLAIN, hasRiver = true),
          TileInfo(65, 62, FLOOD_PLAIN, hasRiver = true),
          TileInfo(127, 29, FOREST, resource = GAME, coveredTerrain = PLAINS, hasRiver = true),
        ),
        image = images.map38
      ),
      MapConfiguration(
        width = 382,
        height = 191,
        tiles = listOf(
          TileInfo(193, 4, DESERT, isIrrigatableViaCityOrLake = true),
          TileInfo(256, 33, HILLS, hasRiver = true),
          TileInfo(320, 66, DESERT, hasRiver = true),
          TileInfo(256, 98, MOUNTAIN, hasRiver = true),
          TileInfo(191, 129, HILLS, hasRiver = true),
          TileInfo(127, 97, FLOOD_PLAIN, resource = WHEAT, hasRiver = true),
          TileInfo(66, 65, DESERT, isIrrigatableViaCityOrLake = true),
          TileInfo(128, 32, DESERT, isIrrigatableViaCityOrLake = true),
        ),
        image = images.map39
      ),
    )
  }
}