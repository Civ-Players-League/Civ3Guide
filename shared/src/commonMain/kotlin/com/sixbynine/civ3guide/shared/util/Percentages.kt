package com.sixbynine.civ3guide.shared.util

import com.sixbynine.civ3guide.shared.ktx.pow
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.formatAsPercentage(decimals: Int = 0): String {
  if (this > 0 && this < 0.005) return "< 1%"
  val digitsBeforeDividing = (this * 10.pow(2 + decimals)).roundToInt()
  if (decimals == 0) return "$digitsBeforeDividing%"

  return (digitsBeforeDividing / 10.0.pow(decimals)).toString() + "%"
}

fun Double.round(decimals: Int = 0): String {
  if (this == this.roundToInt().toDouble()) {
    return roundToInt().toString()
  }
  return ((10.0.pow(decimals) * this).roundToInt() / 10.0.pow(decimals)).toString()
}
