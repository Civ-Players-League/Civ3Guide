package com.sixbynine.civ3guide.shared.ktx

import kotlin.math.pow

fun Int.pow(n: Int): Int {
  // This is mathematically guaranteed to be an integer.
  return this.toDouble().pow(n).toInt()
}