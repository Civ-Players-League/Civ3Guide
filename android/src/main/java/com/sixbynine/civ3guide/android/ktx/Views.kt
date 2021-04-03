package com.sixbynine.civ3guide.android.ktx

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@ColorInt
fun View.getColor(@ColorRes resId: Int): Int {
  return ContextCompat.getColor(context, resId)
}

fun View.getColorStateList(@ColorRes resId: Int): ColorStateList? {
  return ContextCompat.getColorStateList(context, resId)
}

@ColorInt
fun View.getColorAttr(@AttrRes attrId: Int): Int? {
  return getAttrResId(attrId)?.let { getColor(it) }
}

fun View.getColorStateListAttr(@AttrRes attrId: Int): ColorStateList? {
  return getAttrResId(attrId)?.let { getColorStateList(it) }
}

fun View.getDrawable(@DrawableRes resId: Int): Drawable? {
  return ContextCompat.getDrawable(context, resId)
}

fun View.getAttrResId(@AttrRes attrId: Int): Int? {
  val array = context.obtainStyledAttributes(intArrayOf(attrId))
  try {
    return array.getResourceId(0, /* defValue= */ View.NO_ID).takeIf { it != View.NO_ID }
  } finally {
    array.recycle()
  }
}