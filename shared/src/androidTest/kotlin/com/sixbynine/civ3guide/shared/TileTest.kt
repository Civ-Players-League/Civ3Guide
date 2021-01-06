package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.Improvement.*
import com.sixbynine.civ3guide.shared.Resource.CATTLE
import com.sixbynine.civ3guide.shared.Resource.GOLD
import com.sixbynine.civ3guide.shared.Terrain.*
import com.sixbynine.civ3guide.shared.WorkerAction.CLEAR_FOREST
import org.junit.Test
import kotlin.test.assertEquals

class TileTest {

  @Test
  fun expectedOutputs() {
    assertOutput(Tile(GRASSLAND), food = 2)
    assertOutput(Tile(GRASSLAND, hasRiver = true), food = 2, commerce = 1)
    assertOutput(Tile(GRASSLAND, resource = CATTLE), food = 4, shields = 1)
    assertOutput(Tile(GRASSLAND, resource = CATTLE), food = 4, shields = 1)
    assertOutput(Tile(BONUS_GRASSLAND, resource = CATTLE), food = 4, shields = 2)
    assertOutput(Tile(HILLS, listOf(MINE)), food = 1, shields = 3, defence = 0.5)
    assertOutput(
      Tile(HILLS, listOf(MINE, RAILROAD)),
      food = 1,
      shields = 4,
      commerce = 1,
      defence = 0.5
    )
    assertOutput(
      Tile(MOUNTAIN, listOf(MINE, ROAD), GOLD),
      shields = 3,
      commerce = 5,
      defence = 1.0
    )
    assertOutput(Tile(MOUNTAIN, listOf(BARRICADE)), shields = 1, defence = 2.0)
    assertOutput(Tile(FLOOD_PLAIN, listOf(IRRIGATION, RAILROAD)), food = 5, commerce = 1)
  }

  @Test
  fun withWorkerAction() {
    assertEquals(
      expected = Tile(GRASSLAND),
      actual = Tile(FOREST, coveredTerrain = GRASSLAND).withAction(CLEAR_FOREST)
    )
    assertEquals(
      expected = Tile(FLOOD_PLAIN),
      actual = Tile(FOREST, coveredTerrain = FLOOD_PLAIN).withAction(CLEAR_FOREST)
    )
    assertEquals(
      expected = Tile(TUNDRA),
      actual = Tile(FOREST, coveredTerrain = TUNDRA).withAction(CLEAR_FOREST)
    )
  }

  @Test
  fun getOutputBreakdown() {

  }

  private fun assertOutput(
    tile: Tile,
    food: Int = 0,
    shields: Int = 0,
    commerce: Int = 0,
    defence: Double = 0.0
  ) {
    assertEquals(TileOutput(food, shields, commerce, defence), tile.getOutput())
  }
}