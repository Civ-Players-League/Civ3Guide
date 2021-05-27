package com.sixbynine.civ3guide.android.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.BaseActivity
import com.sixbynine.civ3guide.android.notification.NewBetaVersionNotifier
import com.sixbynine.civ3guide.android.util.Logger
import com.sixbynine.civ3guide.shared.external.ExternalLinks.FEEDBACK_EMAIL
import com.sixbynine.civ3guide.shared.external.ExternalLinks.MULTIPLAYER_URL

class HomeActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.adapter = HomeListAdapter()
    recyclerView.layoutManager = LinearLayoutManager(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_home, menu)
    return true
  }
  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_multiplayer -> {
      try {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
          param(FirebaseAnalytics.Param.ITEM_ID, "mutliplayer-menu-item")
        }
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MULTIPLAYER_URL)))
      } catch (e: ActivityNotFoundException) {
        Logger.e("Couldn't launch multiplayer url", e)
        Toast.makeText(this, R.string.error_no_web_browser, Toast.LENGTH_SHORT).show()
      }
      true
    }
    R.id.action_feedback -> {
      val intent =
        Intent(Intent.ACTION_SENDTO)
          .setData(Uri.parse("mailto:$FEEDBACK_EMAIL"))
          .putExtra(Intent.EXTRA_EMAIL, FEEDBACK_EMAIL)
          .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject))
      try {
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
      } catch (e: ActivityNotFoundException) {
        Logger.e("Couldn't launch email", e)
        Toast.makeText(this, R.string.error_no_email_client, Toast.LENGTH_SHORT).show()
      }
      true
    }
    else -> super.onOptionsItemSelected(item)
  }
}
