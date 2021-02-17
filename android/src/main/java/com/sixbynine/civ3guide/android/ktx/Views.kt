package com.sixbynine.civ3guide.android.ktx

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@ColorInt
fun View.getColor(@ColorRes resId: Int): Int {
  return ContextCompat.getColor(context, resId)
}

fun View.getColorStateList(@ColorRes resId: Int): ColorStateList? {
  return ContextCompat.getColorStateList(context, resId)
}