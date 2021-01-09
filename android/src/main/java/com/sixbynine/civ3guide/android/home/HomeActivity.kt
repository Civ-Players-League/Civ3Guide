package com.sixbynine.civ3guide.android.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sixbynine.civ3guide.android.R

class HomeActivity : AppCompatActivity() {

  //private lateinit var puzzleImage: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.adapter = HomeListAdapter()
    recyclerView.layoutManager = LinearLayoutManager(this)

    /*setContentView(layout.activity_main)

    puzzleImage = findViewById(id.puzzle_image)
    puzzleImage.setImageResource(drawable.civ3puzzle1)*/

    //val highlightView = findViewById<ClickHighlightView>(id.click_highlight_view)

    //val drawable = ContextCompat.getDrawable(this, drawable.civ3puzzle1)!!

    /*GlobalScope.launch {
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
    }*/
  }
}
