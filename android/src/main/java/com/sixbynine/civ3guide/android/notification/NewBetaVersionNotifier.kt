package com.sixbynine.civ3guide.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationManagerCompat
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.home.HomeActivity
import com.sixbynine.civ3guide.android.util.Logger
import com.sixbynine.civ3guide.shared.Civ3GuideApplication
import com.sixbynine.civ3guide.shared.preferences

object NewBetaVersionNotifier {

  /** The version of the beta for purposes of showing a notification. */
  private const val BETA_VERSION_SEQUENCE = 4
  private const val BETA_VERSION_FEATURES = "Difficulty levels for combat odds game"

  private const val NOTIFICATION_ID = 1
  private const val KEY_BETA_VERSION_SEQUENCE = "beta_version_sequence_number"
  private const val CHANNEL_ID = "new_beta_version"
  
  fun checkForNewBetaVersion() {
    if (!isBetaVersion()) return

    val previousVersion = preferences.getInt(KEY_BETA_VERSION_SEQUENCE)
    if (previousVersion == BETA_VERSION_SEQUENCE) {
      Logger.d("Previous version had the same beta sequence, not showing a notification")
      return
    }

    if (previousVersion == null) {
      Logger.d("First run, storing the current sequence")
      preferences.putInt(KEY_BETA_VERSION_SEQUENCE, BETA_VERSION_SEQUENCE)
      return
    }

    Logger.d("Upgraded to a new beta version, showing the notification")
    preferences.putInt(KEY_BETA_VERSION_SEQUENCE, BETA_VERSION_SEQUENCE)
    val context = Civ3GuideApplication.instance

    val launchIntent = PendingIntent.getActivity(
      context,
      /* requestCode= */ 0,
      Intent(context, HomeActivity::class.java).setPackage(context.packageName),
      PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setContentTitle("New beta version installed")
      .setContentText(BETA_VERSION_FEATURES)
      .setSmallIcon(R.drawable.ic_notification)
      .setStyle(BigTextStyle().bigText(BETA_VERSION_FEATURES))
      .setContentIntent(launchIntent)
      .setAutoCancel(true)
      .build()

    val notificationManager = NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT >= 26) {
      notificationManager.createNotificationChannel(
        NotificationChannel(CHANNEL_ID, "New Beta version", NotificationManager.IMPORTANCE_LOW)
      )
    }

    notificationManager.notify(NOTIFICATION_ID, notification)

  }

  private fun isBetaVersion(): Boolean {
    val context = Civ3GuideApplication.instance
    val packageManager = context.packageManager
    val packageInfo = try {
      packageManager.getPackageInfo(context.packageName, /* flags= */ 0)
    } catch (e: NameNotFoundException) {
      return false
    }
    return packageInfo.versionName?.endsWith("beta") ?: false
  }
}