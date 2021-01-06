package com.sixbynine.civ3guide.androidApp

import android.graphics.Color
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.sixbynine.civ3guide.shared.Greeting
import androidx.core.content.ContextCompat
import com.sixbynine.civ3guide.shared.TileCoordinate
import com.sixbynine.civ3guide.shared.Point
import com.sixbynine.civ3guide.shared.WorkerPuzzleConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    private lateinit var puzzleImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        puzzleImage = findViewById(R.id.puzzle_image)
        puzzleImage.setImageResource(R.drawable.civ3puzzle1)

        val highlightView = findViewById<ClickHighlightView>(R.id.click_highlight_view)

        val drawable = ContextCompat.getDrawable(this, R.drawable.civ3puzzle1)!!

        GlobalScope.launch {
            val textAsset = readTextAsset("puzzles/puzzle0.json") ?: return@launch
            val configuration = WorkerPuzzleConfiguration.create(textAsset)

            withContext(Dispatchers.Main) {
                val scaleFactor = puzzleImage.getActualScaleRatio()

                fun Point.toGraphicalPointF(): PointF {
                    return PointF(x * scaleFactor, y * scaleFactor)
                }

                fun showClick(cell: TileCoordinate) {
                    val (highlightLeft, highlightTop) = configuration.run {
                        cell.left.toGraphicalPointF() to cell.top.toGraphicalPointF()
                    }
                    val isCorrect = cell == configuration.answerCell
                    highlightView.setHighlightPoints(highlightLeft, highlightTop, if (isCorrect) Color.parseColor("#AA00FF2B") else Color.parseColor("#AAFF0033"))
                }

                @Suppress("ClickableViewAccessibility")
                puzzleImage.setOnTouchListener(
                    object : View.OnTouchListener {

                        private var downTime: Long? = null
                        private var downCell: TileCoordinate? = null

                        override fun onTouch(view: View, event: MotionEvent): Boolean {
                            val point = Point(
                                x = event.x / scaleFactor,
                                y = event.y / scaleFactor
                            )
                            val cell = configuration.getTile(point)
                            when (event.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    downTime = System.currentTimeMillis()
                                    downCell = cell
                                }
                                MotionEvent.ACTION_UP -> {
                                    if (cell != downCell) {
                                        downTime = null
                                        downCell = null
                                        highlightView.setHighlightPoints(null, null, Color.TRANSPARENT)
                                    } else {
                                        showClick(cell)
                                    }
                                    view.performClick()
                                }
                                MotionEvent.ACTION_MOVE -> {
                                    if (cell != downCell) {
                                        downTime = null
                                        downCell = null
                                    }
                                }
                                MotionEvent.ACTION_CANCEL -> {
                                    downTime = null
                                    downCell = null
                                    highlightView.setHighlightPoints(null, null, Color.TRANSPARENT)
                                }
                            }
                            return true
                        }


                    })
            }
        }
    }
}
