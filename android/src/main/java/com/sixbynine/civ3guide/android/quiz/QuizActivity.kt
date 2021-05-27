package com.sixbynine.civ3guide.android.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.BaseActivity
import com.sixbynine.civ3guide.shared.level.LevelPageRowData
import kotlinx.serialization.protobuf.ProtoBuf

class QuizActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_quiz)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
    }

    val rowData =
      ProtoBuf.decodeFromByteArray(
        LevelPageRowData.serializer(),
        intent.getByteArrayExtra(KEY_ROW_DATA)!!
      )
    val level = intent.getIntExtra(KEY_LEVEL, 0)

    val quizController = QuizController(rowData)
    val viewPager = findViewById<ViewPager2>(R.id.view_pager)
    val onNextDoneClicked: () -> Unit = {
      viewPager.adapter?.itemCount?.let { itemCount ->
        if (viewPager.currentItem < itemCount - 1) {
          viewPager.currentItem++
        } else {
          finish()
        }
      }
    }
    viewPager.adapter = QuizViewPagerAdapter(quizController, onNextDoneClicked)
    viewPager.registerOnPageChangeCallback(object: OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        val puzzleIndex = if (rowData.completed < rowData.total) {
          position + rowData.completed
        } else {
          position
        }
        title = getString(R.string.level_s, "${level + 1}, ${puzzleIndex + 1}/${rowData.total}")
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  companion object {
    private const val KEY_ROW_DATA = "key_row_data"
    private const val KEY_LEVEL = "key_level"

    fun createIntent(context: Context, level: Int, levelRow: LevelPageRowData): Intent {
      return Intent(context, QuizActivity::class.java)
        .putExtra(KEY_LEVEL, level)
        .putExtra(KEY_ROW_DATA, ProtoBuf.encodeToByteArray(LevelPageRowData.serializer(), levelRow))
    }
  }
}