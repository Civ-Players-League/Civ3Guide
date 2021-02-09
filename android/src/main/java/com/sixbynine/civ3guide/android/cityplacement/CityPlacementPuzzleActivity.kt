package com.sixbynine.civ3guide.android.cityplacement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sixbynine.civ3guide.android.R

class CityPlacementPuzzleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_city_placement)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
    }

    val viewPager = findViewById<ViewPager2>(R.id.view_pager)
    val controller = CityPlacementPuzzleController()
    viewPager.adapter = CityPlacementPagerAdapter(controller) {
      viewPager.adapter?.let { adapter ->
        if (viewPager.currentItem < adapter.itemCount - 1) {
          viewPager.currentItem++
        } else {
          finish()
        }
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

}