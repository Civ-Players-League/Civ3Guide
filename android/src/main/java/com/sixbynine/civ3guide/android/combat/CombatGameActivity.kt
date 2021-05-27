package com.sixbynine.civ3guide.android.combat

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.BaseActivity
import com.sixbynine.civ3guide.shared.tile.Terrain
import com.sixbynine.civ3guide.shared.combat.CombatPuzzles
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.SPEARMAN
import com.sixbynine.civ3guide.shared.unit.StandardUnitType.WARRIOR
import com.sixbynine.civ3guide.shared.unit.veteran

class CombatGameActivity : BaseActivity() {

  private var showStats = false
  private var advanceToPuzzle = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_combat_game)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    savedInstanceState?.let {
      advanceToPuzzle = it.getBoolean(KEY_ADVANCE_TO_PUZZLE)
    }
    bindViews()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_combat, menu)
    return true
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(KEY_ADVANCE_TO_PUZZLE, advanceToPuzzle)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.findItem(R.id.action_show_stats)?.apply {
      icon = ContextCompat.getDrawable(
        this@CombatGameActivity,
        if (showStats) R.drawable.ic_view_off else R.drawable.ic_view_on
      )
      title = if (showStats) {
        "Hide stats"
      } else {
        "Show stats"
      }
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_show_stats -> {
        showStats = !showStats
        bindViews()
        invalidateOptionsMenu()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun bindViews() {
    if (CombatPuzzles.shouldShowFirstTimePage() && !advanceToPuzzle) {
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
        advanceToPuzzle = true
        bindViews()
      }
      return
    }

    findViewById<CombatGameView>(R.id.game).apply {
      visibility = View.VISIBLE
      setShowStats(showStats)
      newPuzzleListener = {
        if (showStats) {
          showStats = false
          invalidateOptionsMenu()
          bindViews()
        }
      }
    }
    findViewById<View>(R.id.intro_layout).visibility = View.GONE
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  private companion object {
    private const val KEY_ADVANCE_TO_PUZZLE = "advance_to_puzzle"
    private val sampleEngagement = Engagement(
      attacker = veteran(WARRIOR),
      defender = veteran(SPEARMAN),
      terrain = Terrain.GRASSLAND
    )
  }
}