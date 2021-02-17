package com.sixbynine.civ3guide.android.combat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.Terrain
import com.sixbynine.civ3guide.shared.combat.CombatPuzzles
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.unit.MilitaryUnit
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.SPEARMAN
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.WARRIOR
import com.sixbynine.civ3guide.shared.unit.veteran

class CombatGameActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_combat_game)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    bindViews()
  }

  private fun bindViews() {
    if (CombatPuzzles.shouldShowFirstTimePage()) {
      findViewById<View>(R.id.game).visibility = View.GONE
      findViewById<View>(R.id.intro_layout).visibility = View.VISIBLE
      findViewById<CombatUnitView>(R.id.intro_attacker).setData(
        sampleEngagement,
        isAttacker = true
      )
      findViewById<CombatUnitView>(R.id.intro_defender).setData(
        sampleEngagement,
        isAttacker = false
      )
      findViewById<View>(R.id.bottom_button).setOnClickListener {
        CombatPuzzles.noteFirstTimePageSeen()
        bindViews()
      }
      return
    }

    findViewById<View>(R.id.game).visibility = View.VISIBLE
    findViewById<View>(R.id.intro_layout).visibility = View.GONE
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  private companion object {
    private val sampleEngagement = Engagement(
      attacker = veteran(WARRIOR),
      defender = veteran(SPEARMAN),
      terrain = Terrain.GRASSLAND
    )
  }
}