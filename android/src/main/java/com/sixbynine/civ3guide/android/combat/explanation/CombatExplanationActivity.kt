package com.sixbynine.civ3guide.android.combat.explanation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.serialization.protoBuf
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray

class CombatExplanationActivity : AppCompatActivity() {

  private lateinit var engagement: Engagement

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    engagement = protoBuf.decodeFromByteArray(intent.getByteArrayExtra(KEY_ENGAGEMENT)!!)
    setContentView(R.layout.activity_combat_explanation)
  }

  companion object {

    private const val KEY_ENGAGEMENT = "key_engagement"

    fun createIntent(context: Context, engagement: Engagement): Intent {
      return Intent(context, CombatExplanationActivity::class.java)
        .putExtra(KEY_ENGAGEMENT, protoBuf.encodeToByteArray(engagement))
    }
  }
}