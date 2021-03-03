package com.sixbynine.civ3guide.shared.tile

import com.sixbynine.civ3guide.shared.tile.Improvement.*
import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.tile.Terrain.*
import com.sixbynine.civ3guide.shared.tile.TileOutputBreakdown.Modifier
import com.sixbynine.civ3guide.shared.worker.WorkerAction
import dev.icerock.moko.resources.StringResource

data class Tile(
  val terrain: Terrain,
  val improvements: List<Improvement> = emptyList(),
  val resource: Resource? = null,
  val hasRiver: Boolean = false,
  val coveredTerrain: Terrain? = null,
  private val isIrrigatableViaCity: Boolean = false
) {

  init {
    require(terrain !in listOf(FOREST, JUNGLE, MARSH) || coveredTerrain != null) {
      "$terrain requires specifying covered terrain"
    }
  }

  fun withImprovement(improvement: Improvement): Tile {
    return if (improvement in improvements) {
      this
    } else {
      copy(improvements = improvements + improvement)
    }
  }

  fun withAction(workerAction: WorkerAction): Tile {
    val newImprovements = improvements.toMutableList()
    when (workerAction) {
      WorkerAction.MINE -> {
        newImprovements.remove(MINE)
        newImprovements.remove(IRRIGATION)
        newImprovements.add(MINE)
      }
      WorkerAction.IRRIGATE -> {
        newImprovements.remove(MINE)
        newImprovements.remove(IRRIGATION)
        newImprovements.add(IRRIGATION)
      }
      WorkerAction.ROAD -> {
        newImprovements.remove(ROAD)
        newImprovements.add(ROAD)
      }
      WorkerAction.RAILROAD -> {
        newImprovements.remove(RAILROAD)
        newImprovements.add(RAILROAD)
      }
      WorkerAction.CLEAR_FOREST -> {
        check(coveredTerrain != null) {
          "Cannot clear forest without covered terrain specified"
        }
        return copy(
          terrain = coveredTerrain,
          coveredTerrain = null
        )
      }
    }
    return copy(improvements = newImprovements)
  }

  fun getAvailableActions(): List<WorkerAction> {
    return mutableListOf<WorkerAction>().apply {
      if (terrain in ROADABLE_TERRAINS) {
        add(WorkerAction.ROAD)
      }

      if (ROAD in improvements) {
        add(WorkerAction.RAILROAD)
      }

      if (terrain == FOREST) {
        add(WorkerAction.CLEAR_FOREST)
      }

      if (terrain in MINEABLE_TERRAINS) {
        add(WorkerAction.MINE)
      }

      if (terrain in IRRIGATABLE_TERRAINS && (hasRiver || isIrrigatableViaCity)) {
        add(WorkerAction.IRRIGATE)
      }
    }
  }

  /**
   * Returns the output of the tile, based on the terrain, improvements, and resources. This does
   * not take into account penalties or bonuses from governments or golden ages.
   */
  fun getOutput(): TileOutput {
    return getOutputBreakdown().totalOutput
  }

  fun getOutputBreakdown(): TileOutputBreakdown {
    val baseOutput = terrain.getOutput()
    val modifiers = mutableListOf<Modifier>()

    fun addModifier(
      label: StringResource,
      food: Int = 0,
      shields: Int = 0,
      commerce: Int = 0,
      defenceBonus: Double = 0.0
    ) {
      modifiers.add(
        Modifier(
          label,
          TileOutput(food, shields, commerce, defenceBonus)
        )
      )
    }

    if (hasRiver) {
      addModifier(strings.river, commerce = 1)
    }

    if (resource != null) {
      modifiers.add(
        Modifier(
          resource.label,
          resource.getOutput()
        )
      )
    }

    val isRailroaded = RAILROAD in improvements

    if (IRRIGATION in improvements) {
      addModifier(strings.irrigation, food = 1)
      if (isRailroaded) {
        addModifier(strings.railroad, food = 1)
      }
    }

    if (MINE in improvements) {
      addModifier(strings.mine, shields = if (terrain == HILLS || terrain == MOUNTAIN) 2 else 1)
      if (isRailroaded) {
        addModifier(strings.railroad, shields = 1)
      }
    }

    if (ROAD in improvements || isRailroaded) {
      addModifier(strings.road, commerce = 1)
    }

    if (BARRICADE in improvements) {
      addModifier(strings.barricade, defenceBonus = 1.0)
    } else if (FORTRESS in improvements) {
      addModifier(strings.fortress, defenceBonus = 0.5)
    }

    return TileOutputBreakdown(baseOutput, modifiers)
  }

  private companion object {
    val IRRIGATABLE_TERRAINS = listOf(BONUS_GRASSLAND, DESERT, FLOOD_PLAIN, GRASSLAND, PLAINS)
    val MINEABLE_TERRAINS = listOf(BONUS_GRASSLAND, GRASSLAND, PLAINS, HILLS, MOUNTAIN, TUNDRA)
    val ROADABLE_TERRAINS = Terrain.values().toList() - listOf(COAST, LAKE, OCEAN, SEA, VOLCANO)
  }
}

data class TileOutputBreakdown(
  val baseOutput: TileOutput,
  val modifiers: List<Modifier>
) {

  val totalOutput: TileOutput
    get() = modifiers.fold(baseOutput) { acc, modifier -> acc + modifier.effect }

  data class Modifier(val label: StringResource, val effect: TileOutput)
}

enum class Terrain(
  val label: StringResource,
  val food: Int = 0,
  val shields: Int = 0,
  val commerce: Int = 0,
  val defenceBonus: Double = 0.1,
  val movement: Int = 1,
  val disease: Boolean = false
) {
  BONUS_GRASSLAND(strings.bonus_grassland, food = 2, shields = 1),
  COAST(strings.coast, food = 1, commerce = 2),
  DESERT(strings.desert, shields = 1),
  FLOOD_PLAIN(strings.flood_plain, food = 3, disease = true),
  FOREST(strings.forest, food = 1, shields = 2, defenceBonus = 0.25, movement = 2),
  GRASSLAND(strings.grassland, food = 2),
  HILLS(strings.hills, food = 1, shields = 1, defenceBonus = 0.5, movement = 2),
  JUNGLE(strings.jungle, food = 1, defenceBonus = 0.25, movement = 3, disease = true),
  LAKE(strings.lake, food = 2, commerce = 2),
  MARSH(strings.bonus_grassland, food = 1, defenceBonus = 0.2, movement = 2, disease = true),
  MOUNTAIN(strings.mountains, shields = 1, defenceBonus = 1.0, movement = 3),
  OCEAN(strings.ocean),
  PLAINS(strings.plains, food = 1, shields = 1),
  SEA(strings.sea, food = 1, commerce = 1),
  TUNDRA(strings.tundra, food = 1),
  VOLCANO(strings.volcano, shields = 3, defenceBonus = 0.8, movement = 3);

  fun getOutput(): TileOutput {
    return TileOutput(
      food = food,
      shields = shields,
      commerce = commerce,
      defenceBonus = defenceBonus
    )
  }
}

enum class Improvement {
  IRRIGATION,
  MINE,
  FORTRESS,
  BARRICADE,
  OUTPOST,
  AIRFIELD,
  ROAD,
  RAILROAD
}

enum class Resource(
  val label: StringResource,
  val food: Int = 0,
  val shields: Int = 0,
  val commerce: Int = 0
) {
  ALUMINUM(strings.aluminum, shields = 2),
  COAL(strings.coal, shields = 2, commerce = 1),
  HORSES(strings.horses, commerce = 1),
  IRON(strings.iron, shields = 1),
  OIL(strings.oil, shields = 1, commerce = 2),
  RUBBER(strings.rubber, commerce = 2),
  SALTPETER(strings.saltpeter, commerce = 1),
  URANIUM(strings.uranium, shields = 2, commerce = 3),
  DYES(strings.dyes, commerce = 2),
  FURS(strings.furs, shields = 1, commerce = 1),
  GEMS(strings.gems, commerce = 4),
  INCENSE(strings.incense, commerce = 1),
  IVORY(strings.ivory, commerce = 2),
  SILK(strings.silk, commerce = 3),
  SPICE(strings.spices, commerce = 2),
  WINE(strings.wines, food = 1, commerce = 1),
  BANANA(strings.banana, food = 1, commerce = 1),
  CATTLE(strings.cattle, food = 2, shields = 1),
  FISH(strings.fish, food = 2, commerce = 1),
  GAME(strings.game, food = 2),
  GOLD(strings.gold, commerce = 4),
  OASIS(strings.oasis, food = 2),
  SUGAR(strings.sugar, food = 1, commerce = 1),
  TOBACCO(strings.tobacco, commerce = 1),
  WHALE(strings.whales, food = 1, shields = 1, commerce = 2),
  WHEAT(strings.wheat, food = 2);

  fun getOutput() = TileOutput(food, shields, commerce)
}

data class TileOutput(
  val food: Int = 0,
  val shields: Int = 0,
  val commerce: Int = 0,
  val defenceBonus: Double = 0.0
) {
  operator fun plus(other: TileOutput): TileOutput {
    return copy(
      food = food + other.food,
      shields = shields + other.shields,
      commerce = commerce + other.commerce,
      defenceBonus = defenceBonus + other.defenceBonus
    )
  }

  /** Returns whether this [TileOutput] is at least as good as [other] for all attributes. */
  infix fun dominates(other: TileOutput): Boolean {
    return food >= other.food && shields >= other.shields && commerce >= other.commerce &&
        defenceBonus >= other.defenceBonus
  }

  fun isStrictlyBetterThan(other: TileOutput) = this != other && this dominates other
}