package com.sixbynine.civ3guide.android.cityplacement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.android.level.LevelsList
import com.sixbynine.civ3guide.android.level.LevelsList.OnLevelClickListener
import com.sixbynine.civ3guide.shared.cityplacement.CityPlacementProgressManager
import com.sixbynine.civ3guide.shared.level.LevelPageRowData

class CityPlacementHomeActivity : AppCompatActivity() {

  private lateinit var levelsList: LevelsList

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_city_placement_home)
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
    levelsList.setData(CityPlacementProgressManager.getLevelPageData())
    levelsList.listener = object: OnLevelClickListener {
      override fun onLevelClick(index: Int, level: LevelPageRowData) {
        startActivity(
          CityPlacementPuzzleActivity.createIntent(this@CityPlacementHomeActivity, index, level)
        )
      }
    }
  }

}