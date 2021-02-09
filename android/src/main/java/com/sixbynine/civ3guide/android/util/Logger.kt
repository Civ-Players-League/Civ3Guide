package com.sixbynine.civ3guide.android.util

import android.util.Log

object Logger {

  private const val TAG = "Civ3Guide"

  fun d(message: String) {
    Log.d(TAG, message)
  }

}