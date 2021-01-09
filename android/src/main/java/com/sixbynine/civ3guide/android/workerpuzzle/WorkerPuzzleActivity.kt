package com.sixbynine.civ3guide.android.workerpuzzle

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.sixbynine.civ3guide.android.R

class WorkerPuzzleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_worker_puzzle)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
    }

    val viewPager = findViewById<ViewPager2>(R.id.view_pager)
    val controller = PuzzleController()
    viewPager.adapter = PuzzleViewPagerAdapter(controller) {
      viewPager.adapter?.let { adapter ->
        if (viewPager.currentItem < adapter.itemCount - 1) {
          viewPager.currentItem++
        } else {
          finish()
        }
      }
    }
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

}