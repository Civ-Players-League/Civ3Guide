package com.sixbynine.civ3guide.android.combat.explanation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.combat.CombatUnitView
import com.sixbynine.civ3guide.shared.combat.CombatCalculator
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.serialization.protoBuf
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray

class CombatExplanationActivity : AppCompatActivity() {

  private lateinit var engagement: Engagement
  private lateinit var attacker: CombatUnitView
  private lateinit var defender: CombatUnitView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    engagement = protoBuf.decodeFromByteArray(intent.getByteArrayExtra(KEY_ENGAGEMENT)!!)
    setContentView(R.layout.activity_combat_explanation)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    attacker = findViewById(R.id.attacker)
    defender = findViewById(R.id.defender)
    attacker.setData(engagement, isAttacker = true, showStats = true)
    defender.setData(engagement, isAttacker = false, showStats = true)

    val results = CombatCalculator.calculateCombatResults(engagement)
    findViewById<TextView>(R.id.text).text = results.toString()
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  companion object {

    private const val KEY_ENGAGEMENT = "key_engagement"

    fun createIntent(context: Context, engagement: Engagement): Intent {
      return Intent(context, CombatExplanationActivity::class.java)
        .putExtra(KEY_ENGAGEMENT, protoBuf.encodeToByteArray(engagement))
    }
  }
}