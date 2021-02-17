package com.sixbynine.civ3guide.shared

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc

object Resources {

  fun getResource(desc: StringResource): ResourceStringDesc {
    return StringDesc.Resource(desc)
  }
}

fun StringResource.load() = StringDesc.Resource(this).load()

fun StringResource.format(string: String): String {
  return StringDesc.ResourceFormatted(
    this,
    string
  ).load()
}

fun StringResource.format(int: Int): String {
  return StringDesc.ResourceFormatted(
    this,
    int
  ).load()
}

expect fun StringDesc.load(): String