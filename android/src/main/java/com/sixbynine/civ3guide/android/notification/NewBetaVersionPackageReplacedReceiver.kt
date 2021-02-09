package com.sixbynine.civ3guide.android.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NewBetaVersionPackageReplacedReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    NewBetaVersionNotifier.checkForNewBetaVersion()
  }
}