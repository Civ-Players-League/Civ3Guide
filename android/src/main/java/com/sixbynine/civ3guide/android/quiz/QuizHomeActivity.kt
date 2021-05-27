package com.sixbynine.civ3guide.android.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.base.BaseActivity
import com.sixbynine.civ3guide.android.level.LevelsList
import com.sixbynine.civ3guide.android.level.LevelsList.OnLevelClickListener
import com.sixbynine.civ3guide.shared.level.LevelPageRowData
import com.sixbynine.civ3guide.shared.quiz.QuizProgressManager

class QuizHomeActivity : BaseActivity() {
  private lateinit var levelsList: LevelsList

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_quiz_home)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    levelsList = findViewById(R.id.levels_list)
  }

  override fun onResume() {
    super.onResume()
    bindViews()
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  private fun bindViews() {
    levelsList.setData(QuizProgressManager.getLevelPageData())
    levelsList.listener = object: OnLevelClickListener {
      override fun onLevelClick(index: Int, level: LevelPageRowData) {
        startActivity(
          QuizActivity.createIntent(this@QuizHomeActivity, index, level)
        )
      }
    }
  }
}