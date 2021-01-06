package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.Improvement.*
import com.sixbynine.civ3guide.shared.Resource.CATTLE
import com.sixbynine.civ3guide.shared.Resource.IRON
import com.sixbynine.civ3guide.shared.StandardGovernment.*
import com.sixbynine.civ3guide.shared.Terrain.*
import org.junit.Test
import kotlin.test.assertEquals

class GovernmentTest {
  @Test
  fun despotismPenalty() {
    assertEquals(
      DESPOTISM.getOutput(Tile(GRASSLAND)),
      DESPOTISM.getOutput(Tile(GRASSLAND, listOf(IRRIGATION)))
    )
    assertEquals(
      DESPOTISM.getOutput(Tile(BONUS_GRASSLAND)),
      DESPOTISM.getOutput(Tile(BONUS_GRASSLAND, listOf(IRRIGATION)))
    )
    assertEquals(
      TileOutput(food = 3, shields = 1),
      DESPOTISM.getOutput(Tile(GRASSLAND, emptyList(), CATTLE))
    )
    assertEquals(
      TileOutput(food = 4, shields = 1),
      DESPOTISM.getOutput(Tile(GRASSLAND, listOf(IRRIGATION), CATTLE))
    )
    assertEquals(
      TileOutput(food = 1, shields = 2, defenceBonus = 0.5),
      DESPOTISM.getOutput(Tile(HILLS, listOf(MINE)))
    )
    assertEquals(
      TileOutput(food = 1, shields = 3, defenceBonus = 0.5),
      DESPOTISM.getOutput(Tile(HILLS, listOf(MINE), IRON))
    )
  }

  @Test
  fun monarchy() {
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 0),
      MONARCHY.getOutput(Tile(PLAINS, listOf(IRRIGATION)))
    )
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 1),
      MONARCHY.getOutput(Tile(PLAINS, listOf(IRRIGATION, ROAD)))
    )
  }

  @Test
  fun republic() {
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 0),
      REPUBLIC.getOutput(Tile(PLAINS, listOf(IRRIGATION)))
    )
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 2),
      REPUBLIC.getOutput(Tile(PLAINS, listOf(IRRIGATION, ROAD)))
    )
  }

  @Test
  fun democracy() {
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 0),
      DEMOCRACY.getOutput(Tile(PLAINS, listOf(IRRIGATION)))
    )
    assertEquals(
      TileOutput(food = 2, shields = 1, commerce = 2),
      DEMOCRACY.getOutput(Tile(PLAINS, listOf(IRRIGATION, ROAD)))
    )
  }
}