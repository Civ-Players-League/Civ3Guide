package com.sixbynine.civ3guide.android.combat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.google.android.material.button.MaterialButton
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.ktx.getColor
import com.sixbynine.civ3guide.android.ktx.getColorStateList
import com.sixbynine.civ3guide.shared.combat.randomEngagement

class CombatGameView(context: Context, attrs: AttributeSet?) : ScrollView(context, attrs) {

  init {
    View.inflate(context, R.layout.combat_game_contents, this)
  }

  private val attacker: CombatUnitView = findViewById(R.id.attacker)
  private val defender: CombatUnitView = findViewById(R.id.defender)
  private val likelihoodNoButton: MaterialButton = findViewById(R.id.likelihood_no)
  private val likelihoodYesButton: MaterialButton = findViewById(R.id.likelihood_yes)
  private val favorabilityNoButton: MaterialButton = findViewById(R.id.favorability_no)
  private val favorabilityYesButton: MaterialButton = findViewById(R.id.favorability_yes)

  private var engagement =
    randomEngagement(allowUniqueUnits = true, allowFastUnits = true, allowRetreat = false)

  private var likelyChoice: Boolean? = null
  private var favorableChoice: Boolean? = null

  init {
    bindViews()
  }

  private fun bindViews() {
    attacker.setData(engagement, isAttacker = true)
    defender.setData(engagement, isAttacker = false)

    val showResults = likelyChoice != null && favorableChoice != null

    likelihoodNoButton.setTextColor(getColorStateList(R.color.toggle_button_color))
    likelihoodYesButton.setTextColor(getColorStateList(R.color.toggle_button_color))
    favorabilityNoButton.setTextColor(getColorStateList(R.color.toggle_button_color))
    favorabilityYesButton.setTextColor(getColorStateList(R.color.toggle_button_color))

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

  }

}