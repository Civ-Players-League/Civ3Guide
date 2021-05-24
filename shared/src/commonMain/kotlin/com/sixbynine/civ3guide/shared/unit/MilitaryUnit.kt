package com.sixbynine.civ3guide.shared.unit

import com.sixbynine.civ3guide.shared.MR.strings
import com.sixbynine.civ3guide.shared.unit.UnitRank.*
import dev.icerock.moko.resources.StringResource
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind.INT
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class MilitaryUnit(
  val rank: UnitRank,
  val type: UnitType,
  val isFortified: Boolean = false,
  val damage: Int = 0,
) {
  val health: Int
    get() = rank.health - damage

  val fullHealth: Int
    get() = rank.health

  val isDead: Boolean
    get() = health == 0
}

@Serializable(with = UnitRankSerializer::class)
enum class UnitRank(val label: StringResource, val health: Int, val retreatFactor: Int) {
  CONSCRIPT(strings.conscript, health = 2, retreatFactor = 34),
  REGULAR(strings.regular, health = 3, retreatFactor = 50),
  VETERAN(strings.veteran, health = 4, retreatFactor = 58),
  ELITE(strings.elite, health = 5, retreatFactor = 66),
}

@Serializer(forClass = UnitRank::class)
object UnitRankSerializer {
  override fun deserialize(decoder: Decoder): UnitRank {
    return UnitRank.values()[decoder.decodeInt()]
  }

  override val descriptor: SerialDescriptor
    get() = PrimitiveSerialDescriptor("UnitRank", INT)

  override fun serialize(encoder: Encoder, value: UnitRank) {
    encoder.encodeInt(value.ordinal)
  }
}

fun conscript(type: UnitType, isFortified: Boolean = false): MilitaryUnit {
  return MilitaryUnit(CONSCRIPT, type, isFortified)
}

fun regular(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(REGULAR, type, isFortified)

fun veteran(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(VETERAN, type, isFortified)

fun elite(type: UnitType, isFortified: Boolean = false) = MilitaryUnit(ELITE, type, isFortified)