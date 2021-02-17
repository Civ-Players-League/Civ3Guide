package com.sixbynine.civ3guide.android.combat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.updateLayoutParams
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.ktx.getColor

class HPView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

  init {
    clipToOutline = true
  }

  private var total: Int? = null
  private var damage: Int? = null
  private var barHeight: Int? = null
  private var dividerHeight: Int? = null
  private var backgroundPaint: Paint? = null
  private var healthBarPaint: Paint? = null
  private var outlinePaint: Paint? = null

  fun setData(total: Int, damage: Int) {
    this.total = total
    this.damage = damage
    barHeight = resources.getDimensionPixelSize(R.dimen.health_bar_height)
    dividerHeight = resources.getDimensionPixelSize(R.dimen.health_bar_divider_height)

    backgroundPaint = Paint().also { it.color = Color.BLACK }
    healthBarPaint = Paint().also { it.color = healthColor }
    outlinePaint = Paint().also {
      it.color = Color.WHITE
      it.strokeWidth = resources.getDimension(R.dimen.health_bar_outline_width)
      it.style = Paint.Style.STROKE
    }

    updateLayoutParams {
      height = total * barHeight!! + (total - 1) * dividerHeight!!
    }
    invalidate()
  }

  private val healthColor: Int
    get() {
      val damage = damage ?: return Color.TRANSPARENT
      val total = total ?: return Color.TRANSPARENT
      return when {
        damage == total - 1 -> getColor(R.color.red)
        damage == 0 -> getColor(R.color.green)
        damage == 1 && total >= 4 -> getColor(R.color.green)
        else -> getColor(R.color.yellow)
      }
    }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val total = total ?: return
    val damage = damage ?: return
    val barHeight = barHeight ?: return
    val dividerHeight = dividerHeight ?: return
    val backgroundPaint = backgroundPaint ?: return
    val healthBarPaint = healthBarPaint ?: return
    val outlinePaint = outlinePaint ?: return

    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

    repeat(total) {
      val paint = if (it < damage) backgroundPaint else healthBarPaint
      val top = (it * barHeight + it * dividerHeight).toFloat()
      canvas.drawRect(0f, top, width.toFloat(), top + barHeight, paint)
    }

    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), outlinePaint)
  }
}