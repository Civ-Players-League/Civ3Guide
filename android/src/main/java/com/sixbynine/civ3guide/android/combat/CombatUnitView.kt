package com.sixbynine.civ3guide.android.combat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.tile.Terrain.HILLS
import com.sixbynine.civ3guide.shared.combat.Engagement
import com.sixbynine.civ3guide.shared.preferences
import com.sixbynine.civ3guide.shared.setSharedImageResource
import com.sixbynine.civ3guide.shared.setTextResource

class CombatUnitView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

  init {
    View.inflate(context, R.layout.combat_unit_view_contents, this)
  }

  private val defenderAttackerLabel: TextView = findViewById(R.id.defender_attacker_label)
  private val imageLabelContainer: View = findViewById(R.id.unit_image_label_container)
  private val leftHpView: HPView = findViewById(R.id.left_hp)
  private val rightHpView: HPView = findViewById(R.id.right_hp)
  private val image: ImageView = findViewById(R.id.image)
  private val unitName: TextView = findViewById(R.id.unit_name)
  private val health: TextView = findViewById(R.id.health)
  private val fortified: TextView = findViewById(R.id.fortified)
  private val terrain: TextView = findViewById(R.id.terrain)
  private val townStatus: TextView = findViewById(R.id.town_status)
  private val strength: TextView = findViewById(R.id.strength)
  private val cost: TextView = findViewById(R.id.cost)

  private var engagement: Engagement? = null
  private var isAttacker: Boolean = false
  private var showStats: Boolean = false

  fun setData(engagement: Engagement, isAttacker: Boolean, showStats: Boolean = false) {
    this.engagement = engagement
    this.isAttacker = isAttacker
    this.showStats = showStats
    bindViews()
  }

  private fun bindViews() {
    val engagement = engagement ?: return
    val unit = if (isAttacker) engagement.attacker else engagement.defender

    defenderAttackerLabel.setText(if (isAttacker) R.string.attacker else R.string.defender)
    if (isAttacker) {
      leftHpView.visibility = View.VISIBLE
      rightHpView.visibility = View.GONE
      leftHpView.setData(unit.fullHealth, unit.damage)
    } else {
      leftHpView.visibility = View.GONE
      rightHpView.visibility = View.VISIBLE
      rightHpView.setData(unit.fullHealth, unit.damage)
    }

    if (unit.type.altImage == null) {
      imageLabelContainer.isClickable = false
    } else {
      imageLabelContainer.setOnClickListener {
        showAlt = !showAlt
        bindViews()
      }
    }

    image.setSharedImageResource(
      if (showAlt) unit.type.altImage ?: unit.type.image else unit.type.image
    )
    image.scaleX = if (isAttacker) 1f else -1f
    unitName.setTextResource(
      if (showAlt) unit.type.altLabel ?: unit.type.label else unit.type.label
    )

    health.text = "${unit.health}/${unit.fullHealth}"

    if (isAttacker || !unit.isFortified) {
      fortified.visibility = View.GONE
    } else {
      fortified.visibility = View.VISIBLE
    }

    if (isAttacker || (engagement.cityDefenceBonus != null && engagement.terrain != HILLS)) {
      terrain.visibility = View.GONE
    } else {
      terrain.visibility = View.VISIBLE
      terrain.setTextResource(engagement.terrain.label)
    }

    if (isAttacker || engagement.cityDefenceBonus == null) {
      townStatus.visibility = View.GONE
    } else {
      townStatus.visibility = View.VISIBLE
      townStatus.setText(
        when (engagement.cityDefenceBonus) {
          0.0 -> R.string.town
          0.5 -> R.string.town_walls
          1.0 -> R.string.metropolis
          else -> R.string.town
        }
      )
    }

    if (showStats) {
      strength.visibility = View.VISIBLE
      cost.visibility = View.VISIBLE
      if (isAttacker) {
        strength.text = "Attack: ${unit.type.attack}"
      } else {
        strength.text = "Defence: ${unit.type.defence}"
      }
      cost.text = "Cost: ${unit.type.cost}"
    } else {
      strength.visibility = View.GONE
      cost.visibility = View.GONE
    }
  }

  private companion object {
    const val PREFS_KEY_SHOW_ALT = "show_alt_images"

    private var _showAlt: Boolean? = null

    var showAlt: Boolean
      get() {
        _showAlt?.let { return it }
        return (preferences.getBoolean(PREFS_KEY_SHOW_ALT) ?: false).also {
          _showAlt = it
        }
      }
      set(value) {
        _showAlt = value
        preferences.putBoolean(PREFS_KEY_SHOW_ALT, value)
      }
  }
}