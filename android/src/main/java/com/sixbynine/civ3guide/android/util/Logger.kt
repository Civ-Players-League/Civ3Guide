package com.sixbynine.civ3guide.android.util

import android.util.Log
import com.sixbynine.civ3guide.android.BuildConfig

object Logger {

  private const val TAG = "Civ3Guide"

  fun d(message: String) {
    if (BuildConfig.DEBUG) {
      Log.d(TAG, message)
    }
  }

  fun i(message: String) {
    Log.i(TAG, message)
  }

  fun w(message: String, t: Throwable? = null) {
    Log.w(TAG, message, t)
  }

  fun e(message: String, t: Throwable? = null) {
    Log.e(TAG, message, t)
  }

}