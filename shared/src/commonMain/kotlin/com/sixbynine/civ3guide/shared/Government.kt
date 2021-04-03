package com.sixbynine.civ3guide.shared

import com.sixbynine.civ3guide.shared.tile.Tile
import com.sixbynine.civ3guide.shared.tile.TileOutput
import com.sixbynine.civ3guide.shared.tile.TileOutputBreakdown
import com.sixbynine.civ3guide.shared.tile.TileOutputBreakdown.Modifier

interface Government {
  fun getOutput(tile: Tile, isAgricultural: Boolean): TileOutput

  fun getOutputBreakdown(tile: Tile, isAgricultural: Boolean): TileOutputBreakdown
}

enum class StandardGovernment : Government {
  ANARCHY,
  DESPOTISM,
  MONARCHY,
  REPUBLIC,
  FEUDALISM,
  DEMOCRACY,
  FASCISM,
  COMMUNISM;

  override fun getOutput(tile: Tile, isAgricultural: Boolean): TileOutput {
    return getOutputBreakdown(tile, isAgricultural).totalOutput
  }

  override fun getOutputBreakdown(tile: Tile, isAgricultural: Boolean): TileOutputBreakdown {
    val baseOutputBreakdown = tile.getOutputBreakdown(isAgricultural)
    val baseOutput = baseOutputBreakdown.totalOutput
    return when (this) {
      ANARCHY, DESPOTISM -> {
        var penalty = TileOutput()
        var hasPenalty = false
        if (baseOutput.food > 2) {
          penalty = penalty.copy(food = -1)
          hasPenalty = true
        }
        if (baseOutput.shields > 2) {
          penalty = penalty.copy(shields = -1)
          hasPenalty = true
        }
        if (baseOutput.commerce > 2) {
          penalty = penalty.copy(commerce = -1)
          hasPenalty = true
        }
        if (hasPenalty) {
          baseOutputBreakdown.copy(
            modifiers = baseOutputBreakdown.modifiers + Modifier(
              label = MR.strings.despotism_penalty,
              effect = penalty
            )
          )
        } else {
          baseOutputBreakdown
        }
      }
      REPUBLIC, DEMOCRACY -> {
        if (baseOutput.commerce > 0) {
          baseOutputBreakdown.copy(
            modifiers = baseOutputBreakdown.modifiers + Modifier(
              label = MR.strings.commerce_bonus,
              effect = TileOutput(commerce = 1)
            )
          )
        } else {
          baseOutputBreakdown
        }
      }
      else -> baseOutputBreakdown
    }
  }
}