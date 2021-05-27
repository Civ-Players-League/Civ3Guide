package com.sixbynine.civ3guide.android.combat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.firebaseAnalytics
import com.sixbynine.civ3guide.android.combat.explanation.CombatExplanationActivity
import com.sixbynine.civ3guide.android.ktx.getColorStateList
import com.sixbynine.civ3guide.shared.combat.*
import com.sixbynine.civ3guide.shared.combat.CombatResultType.ATTACKER_WINS

class CombatGameView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

  var newPuzzleListener: (() -> Unit)? = null

  init {
    View.inflate(context, R.layout.combat_game_contents, this)
  }

  private val attacker: CombatUnitView = findViewById(R.id.attacker)
  private val defender: CombatUnitView = findViewById(R.id.defender)
  private val likelihoodNoButton: MaterialButton = findViewById(R.id.likelihood_no)
  private val likelihoodYesButton: MaterialButton = findViewById(R.id.likelihood_yes)
  private val favorabilityNoButton: MaterialButton = findViewById(R.id.favorability_no)
  private val favorabilityYesButton: MaterialButton = findViewById(R.id.favorability_yes)
  private val winPercentage: TextView = findViewById(R.id.win_percentage)
  private val costRatio: TextView = findViewById(R.id.cost_ratio)
  private val expectedShields: TextView = findViewById(R.id.expected_shields)
  private val favorabilitySummary: TextView = findViewById(R.id.favorability_summary)
  private val nextPuzzleButton: View = findViewById(R.id.next_puzzle_button)
  private val explainButton: View = findViewById(R.id.explain_button)
  private val river: View = findViewById(R.id.river)
  private val difficultyLevelSpinner: Spinner = findViewById(R.id.difficulty_level_spinner)
  private val difficultyLayout: View = findViewById(R.id.difficulty_layout)

  private var engagement =
    randomEngagement(
      allowUniqueUnits = true,
      allowFastUnits = true,
      allowRetreat = false,
    )

  private var likelyChoice: Boolean? = null
  private var favorableChoice: Boolean? = null
  private var showStats = false

  init {
    bindViews()
  }

  fun setShowStats(showStats: Boolean) {
    this.showStats = showStats
    bindViews()
  }

  private fun bindViews() {
    attacker.setData(engagement, isAttacker = true, showStats = showStats)
    defender.setData(engagement, isAttacker = false, showStats = showStats)

    likelihoodNoButton.isChecked = likelyChoice == false
    likelihoodYesButton.isChecked = likelyChoice == true
    likelihoodNoButton.setOnClickListener {
      likelyChoice = false
      bindViews()
    }
    likelihoodYesButton.setOnClickListener {
      likelyChoice = true
      bindViews()
    }

    favorabilityNoButton.isChecked = favorableChoice == false
    favorabilityYesButton.isChecked = favorableChoice == true
    favorabilityNoButton.setOnClickListener {
      favorableChoice = false
      bindViews()
    }
    favorabilityYesButton.setOnClickListener {
      favorableChoice = true
      bindViews()
    }

    river.visibility = if (engagement.acrossRiver) {
      View.VISIBLE
    } else {
      View.INVISIBLE
    }

    val showResults = likelyChoice != null && favorableChoice != null
    if (!showResults) {
      favorabilityYesButton.bindColors(null)
      favorabilityNoButton.bindColors(null)
      likelihoodYesButton.bindColors(null)
      likelihoodNoButton.bindColors(null)
      winPercentage.visibility = View.GONE
      costRatio.visibility = View.GONE
      expectedShields.visibility = View.GONE
      favorabilitySummary.visibility = View.GONE
      nextPuzzleButton.visibility = View.GONE
      explainButton.visibility = View.GONE
      difficultyLayout.visibility = View.GONE
      return
    }

    val results = CombatCalculator.calculateCombatResults(engagement)

    favorabilityYesButton.bindColors(results.attackerFavorability >= 0.995)
    favorabilityNoButton.bindColors(results.attackerFavorability < 1.005)

    likelihoodYesButton.bindColors(results.p(ATTACKER_WINS) >= 0.4995)
    likelihoodNoButton.bindColors(results.p(ATTACKER_WINS) < 0.5005)

    winPercentage.visibility = View.VISIBLE
    val pWinString = "${String.format("%.1f", results.p(ATTACKER_WINS) * 100)}%"
    winPercentage.text = context.getString(R.string.p_win, pWinString)

    costRatio.visibility = View.VISIBLE
    val costRatioString = "${engagement.attacker.type.cost} vs ${engagement.defender.type.cost}"
    costRatio.text = "Cost: $costRatioString"

    expectedShields.visibility = View.VISIBLE
    val eSString = results.expectedShields.let {
      if (it > 0) {
        String.format("+%.1f", it)
      } else {
        String.format("%.1f", it)
      }
    }
    expectedShields.text = context.getString(R.string.expected_shields, eSString)

    favorabilitySummary.visibility = View.VISIBLE
    val favorabilitySummaryLine = when {
      results.attackerFavorability <= 0.2 -> R.string.very_unfavorable
      results.attackerFavorability <= 0.8 -> R.string.unfavorable
      results.attackerFavorability < 1.0 -> R.string.slightly_unfavorable
      results.attackerFavorability == 1.0 -> R.string.even
      results.attackerFavorability <= 1.2 -> R.string.slightly_favorable
      results.attackerFavorability <= 2.0 -> R.string.favorable
      else -> R.string.very_favorable
    }
    favorabilitySummary.text = "(${resources.getString(favorabilitySummaryLine)})"

    nextPuzzleButton.visibility = View.VISIBLE
    nextPuzzleButton.setOnClickListener {
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
        param(FirebaseAnalytics.Param.ITEM_ID, "next-combat-puzzle")
      }
      likelyChoice = null
      favorableChoice = null
      engagement =
        randomEngagement(
          allowUniqueUnits = true,
          allowFastUnits = true,
          allowRetreat = false,
        )
      newPuzzleListener?.invoke()
      bindViews()
    }

    explainButton.visibility = View.VISIBLE
    explainButton.setOnClickListener {
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
        param(FirebaseAnalytics.Param.ITEM_ID, "explain-combat-puzzle")
      }
      context.startActivity(CombatExplanationActivity.createIntent(context, engagement))
    }

    difficultyLayout.visibility = View.VISIBLE
    if (difficultyLevelSpinner.adapter == null) {
      difficultyLevelSpinner.adapter =
        ArrayAdapter(
          context,
          R.layout.simple_spinner_item,
          Difficulty.values().map { it.displayName }
        )
      difficultyLevelSpinner.setSelection(CombatDifficultyManager.difficulty.number)
      difficultyLevelSpinner.onItemSelectedListener = object: OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          val difficulty = Difficulty.values()[position]
          Firebase.analytics.setUserProperty(
            "combat_game_difficulty",
            CombatDifficultyManager.difficulty.displayName
          )
          CombatDifficultyManager.difficulty = difficulty
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
      }
    }


  }

  private fun MaterialButton.bindColors(isCorrect: Boolean?) {
    when (isCorrect) {
      null -> {
        setTextColor(getColorStateList(R.color.toggle_text_color))
        setStrokeColorResource(R.color.toggle_stroke_color)
        backgroundTintList = getColorStateList(R.color.toggle_background_color)
      }
      false -> {
        setTextColor(getColorStateList(R.color.incorrect_toggle_text_color))
        setStrokeColorResource(R.color.incorrect_toggle_stroke_color)
        backgroundTintList = getColorStateList(R.color.incorrect_toggle_background_color)
      }
      true -> {
        setTextColor(getColorStateList(R.color.correct_toggle_text_color))
        setStrokeColorResource(R.color.correct_toggle_stroke_color)
        backgroundTintList = getColorStateList(R.color.correct_toggle_background_color)
      }
    }
  }

}