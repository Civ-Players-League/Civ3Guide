package com.sixbynine.civ3guide.android.ktx

import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.annotation.IdRes

fun ViewGroup.indexOfChildWithId(@IdRes id: Int): Int? {
  return (0 until childCount).firstOrNull { getChildAt(it).id == id }
}

fun RadioGroup.checkAt(index: Int?) {
  if (index == null) {
    if (checkedRadioButtonId != -1) clearCheck()
    return
  }
  val radioButton = getChildAt(index) ?: run {
    clearCheck()
    return
  }
  if (checkedRadioButtonId != radioButton.id) {
    check(radioButton.id)
  }
}

val RadioGroup.checkedRadioButtonIndex: Int?
  get() = indexOfChildWithId(checkedRadioButtonId)