package com.sixbynine.civ3guide.android.workerpuzzle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ClickHighlightView(context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var path: Path? = null

    private val paint = Paint().apply {
        color = Color.argb(88, 255, 255, 255)
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.ROUND
    }

    fun setHighlightPoints(left: PointF?, top: PointF?, color: Int) {
        if (left == null || top == null) {
            path = null
            invalidate()
            return
        }

        path = Path().apply {
            moveTo(left.x, left.y)
            lineTo(top.x, top.y)
            lineTo(top.x * 2 - left.x, left.y)
            lineTo(top.x, left.y * 2 - top.y)
            lineTo(left.x, left.y)
        }
        paint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path?.let { canvas.drawPath(it, paint) }
    }
}