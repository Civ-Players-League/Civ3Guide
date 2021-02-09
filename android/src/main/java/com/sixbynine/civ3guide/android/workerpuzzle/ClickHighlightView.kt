package com.sixbynine.civ3guide.android.workerpuzzle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ClickHighlightView(context: Context, attrs: AttributeSet?): View(context, attrs) {

    private val highlights = arrayListOf<Pair<Path, Int>>()

    private val paint = Paint().apply {
        color = Color.argb(88, 255, 255, 255)
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.ROUND
    }

    fun addHighlight(left: PointF, top: PointF, color: Int) {
        val path = Path().apply {
            moveTo(left.x, left.y)
            lineTo(top.x, top.y)
            lineTo(top.x * 2 - left.x, left.y)
            lineTo(top.x, left.y * 2 - top.y)
            lineTo(left.x, left.y)
        }
        highlights.add(path to color)
        invalidate()
    }

    fun clearHighlights() {
        highlights.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        highlights.forEach { (path, color) ->
            paint.color = color
            canvas.drawPath(path, paint)
        }
    }

    companion object {
        val COLOR_SELECTED = Color.argb(83, 0, 0, 255)
        val COLOR_SELECTABLE = Color.argb(83, 255, 255, 255)
    }
}