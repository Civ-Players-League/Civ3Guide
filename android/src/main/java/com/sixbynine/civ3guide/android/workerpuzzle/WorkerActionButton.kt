package com.sixbynine.civ3guide.android.workerpuzzle

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.ImageButton
import com.sixbynine.civ3guide.android.R
import com.sixbynine.civ3guide.shared.WorkerAction
import com.sixbynine.civ3guide.shared.setSharedImageResource

class WorkerActionButton(
  context: Context,
  attrs: AttributeSet?
): FrameLayout(context, attrs), Checkable {

  init {
    View.inflate(context, R.layout.view_worker_action_button_contents, this)
  }

  private val background = findViewById<View>(R.id.background)
  private val image = findViewById<ImageButton>(R.id.image)

  private var _workerAction: WorkerAction? = null
  var workerAction: WorkerAction?
    get() = _workerAction
    set(value) {
      _workerAction = value
      rebind()
    }

  var actionClickListener: ((WorkerAction) -> Unit)? = null

  private var checked = false

  override fun setChecked(checked: Boolean) {
    this.checked = checked
    rebind()
  }

  override fun isChecked(): Boolean {
    return checked
  }

  override fun toggle() {
    checked = !checked
    rebind()
  }

  private fun rebind() {
    if (checked) {
      background.setBackgroundResource(R.drawable.worker_action_button_background_checked)
    } else {
      background.background = null
    }

    val localWorkerAction = _workerAction
    if (localWorkerAction == null) {
      image.setImageDrawable(null)
      image.setOnClickListener(null)
    } else {
      image.setSharedImageResource(localWorkerAction.image)
      image.setOnClickListener {
        actionClickListener?.invoke(localWorkerAction)
      }
    }
  }
}