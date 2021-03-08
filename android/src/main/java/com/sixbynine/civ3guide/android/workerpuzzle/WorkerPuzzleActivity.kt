package com.sixbynine.civ3guide.android.workerpuzzle

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.level.LevelPageRowData
import kotlinx.serialization.protobuf.ProtoBuf

class WorkerPuzzleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_worker_puzzle)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
    }

    val rowData =
      ProtoBuf.decodeFromByteArray(
        LevelPageRowData.serializer(),
        intent.getByteArrayExtra(KEY_ROW_DATA)!!
      )
    val level = intent.getIntExtra(KEY_LEVEL, 0)

    val viewPager = findViewById<ViewPager2>(R.id.view_pager)
    val controller = PuzzleController(rowData)
    viewPager.adapter = PuzzleViewPagerAdapter(controller) {
      viewPager.adapter?.let { adapter ->
        if (viewPager.currentItem < adapter.itemCount - 1) {
          viewPager.currentItem++
        } else {
          finish()
        }
      }
    }
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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_puzzle, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.help -> {
        HelpDialogFragment().showNow(supportFragmentManager, "HelpDialog")
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }

  class HelpDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
      return AlertDialog.Builder(requireContext())
        .setTitle(R.string.home_label_worker_action_title)
        .setMessage(R.string.worker_puzzle_help_text)
        .create()
    }
  }

  companion object {
    private const val KEY_ROW_DATA = "key_row_data"
    private const val KEY_LEVEL = "key_level"

    fun createIntent(context: Context, level: Int, levelRow: LevelPageRowData): Intent {
      return Intent(context, WorkerPuzzleActivity::class.java)
        .putExtra(KEY_LEVEL, level)
        .putExtra(KEY_ROW_DATA, ProtoBuf.encodeToByteArray(LevelPageRowData.serializer(), levelRow))
    }
  }

}