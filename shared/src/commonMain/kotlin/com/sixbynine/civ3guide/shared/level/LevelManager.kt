package com.sixbynine.civ3guide.shared.level

import com.sixbynine.civ3guide.shared.concurrent.atomicRef
import com.sixbynine.civ3guide.shared.preferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber
import kotlin.math.ceil

object LevelManager {

  const val PUZZLES_PER_ROW = 5

  private val idToData = atomicRef(mapOf<LevelId, LevelData>())

  fun notePuzzleSolved(id: LevelId, index: Int) {
    val puzzleId = LeveledPuzzleId(
      level = index / PUZZLES_PER_ROW,
      puzzle = index % PUZZLES_PER_ROW,
      index = index
    )
    getLastSolvedPuzzleId(id)?.let { if (it >= puzzleId) return }
    updateLevelData(id, LevelData(puzzleId))
  }

  fun getLastSolvedPuzzleId(id: LevelId): LeveledPuzzleId? {
    return getLevelData(id).lastSolvedPuzzle
  }

  fun getLevelData(id: LevelId): LevelData {
    val idToDataCopy = idToData.get()
    idToDataCopy[id]?.let { return it }
    val serialized = preferences.getString(id.prefsKey)
    val storedData = if (serialized == null) {
      LevelData()
    } else {
      ProtoBuf.decodeFromHexString(serialized)
    }

    idToData.set(idToDataCopy + (id to storedData))
    return storedData
  }

  fun updateLevelData(id: LevelId, data: LevelData) {
    val idToDataCopy = idToData.get()
    idToData.set(idToDataCopy + (id to data))
    preferences.putString(id.prefsKey, ProtoBuf.encodeToHexString(data))
  }

  fun getLevelPageData(id: LevelId, totalCount: Int): LevelPageData {
    val lastSolvedPuzzleId = getLevelData(id).lastSolvedPuzzle
    val numRows = ceil(totalCount / PUZZLES_PER_ROW.toDouble()).toInt()
    val rows = (0 until numRows).map { rowIndex ->
      val puzzlesInRow = if (rowIndex == numRows - 1) {
        (totalCount % PUZZLES_PER_ROW).let { if (it == 0) PUZZLES_PER_ROW else it}
      } else {
        PUZZLES_PER_ROW
      }

      val numCompleted = if (lastSolvedPuzzleId == null) {
        0
      } else {
        when (rowIndex) {
          in 0 until lastSolvedPuzzleId.level -> puzzlesInRow
          lastSolvedPuzzleId.level -> lastSolvedPuzzleId.puzzle + 1
          else -> 0
        }
      }

      val launchIndex = if (numCompleted == puzzlesInRow) {
        rowIndex * PUZZLES_PER_ROW
      } else {
        rowIndex * PUZZLES_PER_ROW + numCompleted
      }

      val isUnlocked =
        numCompleted > 0 ||
            rowIndex == 0 ||
            (lastSolvedPuzzleId?.level == rowIndex - 1 &&
                lastSolvedPuzzleId.puzzle == PUZZLES_PER_ROW - 1)

      LevelPageRowData(
        completed = numCompleted,
        total = puzzlesInRow,
        launchIndex = launchIndex,
        isLocked = !isUnlocked
      )
    }

    return LevelPageData(rows)
  }

  private val LevelId.prefsKey: String
    get() = "level_data_$number"
}

enum class LevelId(internal val number: Int) {
  WORKER_ACTION(0), CITY_PLACEMENT(1), EARLY_GAME_QUIZ(2)
}

@Serializable
data class LevelData(
  @ProtoNumber(1) val lastSolvedPuzzle: LeveledPuzzleId? = null
)

@Serializable
data class LeveledPuzzleId(
  @ProtoNumber(1) val level: Int = 0,
  @ProtoNumber(2) val puzzle: Int = 0,
  @ProtoNumber(3) val index: Int = 0
) : Comparable<LeveledPuzzleId> {
  override fun compareTo(other: LeveledPuzzleId): Int {
    return index.compareTo(other.index)
  }
}

data class LevelPageData(
  val rows: List<LevelPageRowData>
)

data class LevelPageRowData(
  val completed: Int = 0,
  val total: Int = 0,
  val launchIndex: Int = 0,
  val isLocked: Boolean = false
)

