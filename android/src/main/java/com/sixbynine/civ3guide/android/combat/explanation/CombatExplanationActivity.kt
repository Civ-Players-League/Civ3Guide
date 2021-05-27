package com.sixbynine.civ3guide.android.combat.explanation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.BaseActivity
import com.sixbynine.civ3guide.android.combat.CombatUnitView
import com.sixbynine.civ3guide.shared.combat.CombatCalculator
import com.sixbynine.civ3guide.shared.combat.CombatExplainer
import com.sixbynine.civ3guide.shared.combat.CombatResult
import com.sixbynine.civ3guide.shared.combat.CombatResultType.ATTACKER_WINS
import com.sixbynine.civ3guide.shared.combat.CombatResultType.DEFENDER_WINS
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.serialization.protoBuf
import com.sixbynine.civ3guide.shared.util.formatAsPercentage
import com.sixbynine.civ3guide.shared.util.round
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray

class CombatExplanationActivity : BaseActivity() {

  private lateinit var engagement: Engagement
  private lateinit var attacker: CombatUnitView
  private lateinit var defender: CombatUnitView
  private lateinit var river: View
  private lateinit var effectiveDefenceHeader: TextView
  private lateinit var effectiveDefenceContainer: ViewGroup
  private lateinit var attackerWinsHeader: TextView
  private lateinit var attackerWinContainer: ViewGroup
  private lateinit var defenderWinsHeader: TextView
  private lateinit var defenderWinContainer: ViewGroup
  private lateinit var expectedShieldsHeader: TextView
  private lateinit var expectedShieldsContainer: ViewGroup

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    engagement = protoBuf.decodeFromByteArray(intent.getByteArrayExtra(KEY_ENGAGEMENT)!!)
    setContentView(R.layout.activity_combat_explanation)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    attacker = findViewById(R.id.attacker)
    defender = findViewById(R.id.defender)
    river = findViewById(R.id.river)
    attackerWinsHeader = findViewById(R.id.attacker_wins_header)
    attackerWinContainer = findViewById(R.id.attacker_win_rows_container)
    defenderWinsHeader = findViewById(R.id.defender_wins_header)
    defenderWinContainer = findViewById(R.id.defender_win_rows_container)
    effectiveDefenceHeader = findViewById(R.id.effective_defence_header)
    effectiveDefenceContainer = findViewById(R.id.effective_defence_rows_container)
    expectedShieldsHeader = findViewById(R.id.expected_shields_header)
    expectedShieldsContainer = findViewById(R.id.expected_shields_row_container)

    attacker.setData(engagement, isAttacker = true, showStats = true)
    defender.setData(engagement, isAttacker = false, showStats = true)
    river.visibility = if (engagement.acrossRiver) {
      View.VISIBLE
    } else {
      View.INVISIBLE
    }

    val results = CombatCalculator.calculateCombatResults(engagement)

    attackerWinsHeader.text =
      getString(
        R.string.probability_attacker_wins_header,
        results.p(ATTACKER_WINS).formatAsPercentage(decimals = 1)
      )

    results
      .results
      .filter { it.defender.isDead }
      .sortedByDescending { it.attacker.health }
      .forEach {
        addCombatResultRow(attackerWinContainer, it)
      }

    defenderWinsHeader.text =
      getString(
        R.string.probability_defender_wins_header,
        results.p(DEFENDER_WINS).formatAsPercentage(decimals = 1)
      )

    results
      .results
      .filter { it.attacker.isDead }
      .sortedByDescending { it.defender.health }
      .forEach {
        addCombatResultRow(defenderWinContainer, it)
      }

    val defenceExplanation = CombatExplainer.getDefenceExplanation(engagement)
    effectiveDefenceHeader.text =
      getString(
        R.string.effective_defence_header,
        defenceExplanation.effectiveDefence.round(decimals = 2)
      )

    addExplanationRow(
      effectiveDefenceContainer,
      "\t" + getString(R.string.base_defence_x, engagement.defender.type.defence.toString())
    )

    defenceExplanation.bonusDescriptions.forEach {
      addExplanationRow(effectiveDefenceContainer, it)
    }

    expectedShieldsHeader.text =
      getString(R.string.expected_shields, results.expectedShields.round(decimals = 1))
    addExplanationRow(expectedShieldsContainer, getString(R.string.expected_shields_formula))
    addExplanationRow(
      expectedShieldsContainer,
      "\t = " +
          results.p(ATTACKER_WINS).formatAsPercentage(decimals = 1) +
          " x " +
          engagement.defender.type.cost +
          " - " +
          results.p(DEFENDER_WINS).formatAsPercentage(decimals = 1) +
          " x " +
          engagement.attacker.type.cost
    )
    addExplanationRow(
      expectedShieldsContainer,
      "\t = " +
          (results.p(ATTACKER_WINS) * engagement.defender.type.cost).round(decimals = 1) +
          " - " +
          (results.p(DEFENDER_WINS) * engagement.attacker.type.cost).round(decimals = 1)
    )
    addExplanationRow(
      expectedShieldsContainer,
      "\t = " + results.expectedShields.round(decimals = 1)
    )

  }

  private fun addCombatResultRow(parent: ViewGroup, result: CombatResult) {
    val remainingHealth =
      if (result.attacker.isDead) result.defender.health else result.attacker.health
    addExplanationRow(
      parent,
      "\tWith $remainingHealth health: ${result.probability.formatAsPercentage(decimals = 2)}"
    )
  }

  private fun addExplanationRow(parent: ViewGroup, text: String) {
    val row =
      layoutInflater.inflate(R.layout.combat_explanation_row, parent, false) as TextView
    row.text = text
    parent.addView(row)
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