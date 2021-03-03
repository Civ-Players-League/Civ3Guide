package com.sixbynine.civ3guide.shared.level

import com.sixbynine.civ3guide.shared.concurrent.atomicRef
import com.sixbynine.civ3guide.shared.preferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber

object LevelManager {

  private val idToData = atomicRef(mapOf<LevelId, LevelData>())

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

  private val LevelId.prefsKey: String
    get() = "level_data_$number"
}

enum class LevelId(internal val number: Int) {
  WORKER_ACTION(0)
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
): Comparable<LeveledPuzzleId> {
  override fun compareTo(other: LeveledPuzzleId): Int {
    return index.compareTo(other.index)
  }
}

