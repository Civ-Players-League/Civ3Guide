package com.sixbynine.civ3guide.shared.serialization

import com.sixbynine.civ3guide.shared.unit.StandardUnitType
import com.sixbynine.civ3guide.shared.unit.UnitType
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.protobuf.ProtoBuf

val protoBuf = ProtoBuf {
  serializersModule = SerializersModule {
    polymorphic(UnitType::class, StandardUnitType::class, StandardUnitType.serializer())
  }
}