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

fun StringResource.format(arg1: String): String {
  return StringDesc.ResourceFormatted(
    this,
    arg1
  ).load()
}

fun StringResource.format(arg1: Int): String {
  return StringDesc.ResourceFormatted(
    this,
    arg1
  ).load()
}

expect fun StringDesc.load(): String