package com.sixbynine.civ3guide.shared

import android.app.Application

open class Civ3GuideApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    _instance = this
  }

  companion object {
    private var _instance: Civ3GuideApplication? = null

    val instance: Civ3GuideApplication
      get() {
        return _instance ?: throw IllegalStateException(
          "Cannot use application instance before Application.onCreate() is called"
        )
      }
  }
}