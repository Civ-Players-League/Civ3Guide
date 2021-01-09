package com.sixbynine.civ3guide.android.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sixbynine.civ3guide.android.R

class QuizActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_quiz)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
    }

    val quizController = QuizController()
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
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }
}